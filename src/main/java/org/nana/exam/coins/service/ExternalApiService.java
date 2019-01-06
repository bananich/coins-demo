package org.nana.exam.coins.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.nana.exam.coins.client.ExternalApi;
import org.nana.exam.coins.dto.CoinExamDTO;
import org.nana.exam.coins.external.dto.PriceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ExternalApiService {

	private static final int CHUNK_SIZE = 20;

	@Autowired
	ExternalApi externalApi;

	@Autowired
	CacheService cacheService;

	long lastCallTimeStamp = System.currentTimeMillis();

	List<CoinExamDTO> cachedCoins = null;

	public CoinExamDTO getCoin(String symbol) {
		fetchCoins();
		return cachedCoins.stream().filter(c -> symbol.equals(c.getSymbol())).findFirst().orElse(null);
	}

	public List<CoinExamDTO> getCoinNames(Optional<String> algorithm, Optional<List<String>> symbols) {
		fetchCoins();
		List<CoinExamDTO> tempList = cachedCoins;
		if (algorithm.isPresent()) {
			tempList = tempList.stream().filter(c -> c.getAlgorithm().equals(algorithm.get()))
					.collect(Collectors.toList());
		}
		if (symbols.isPresent()) {
			tempList = tempList.stream()
					.filter(c -> symbols.get().stream().filter(cc -> cc.equals(c.getSymbol())).findFirst().isPresent())
					.collect(Collectors.toList());
		}
		return tempList.parallelStream().map(c -> CoinExamDTO.builder().coinName(c.getCoinName()).build())
				.collect(Collectors.toList());
	}

	private void fetchCoins() {
		if (cachedCoins == null || System.currentTimeMillis() - lastCallTimeStamp > cacheService.getCacheTtl()) {
			cachedCoins = externalApi.listAllCoins().getData().values().stream()
					.map(c -> CoinExamDTO.builder().id(Integer.parseInt(c.getId())).symbol(c.getSymbol())
							.algorithm(c.getAlgorithm()).coinName(c.getCoinName()).build())
					.collect(Collectors.toList());
			Map<String, PriceData> prices = new HashMap<>();
			Set<String> symbols = cachedCoins.stream().map(CoinExamDTO::getSymbol).collect(Collectors.toSet());
			AtomicInteger counter = new AtomicInteger();
			symbols.stream().collect(Collectors.groupingBy(c -> counter.getAndIncrement() / CHUNK_SIZE)).forEach((i, v) -> {
				String join = String.join(",", v);
				try {
					prices.putAll(externalApi.getPrices(join));
				} catch (Exception e) {
					System.err.println(e.getMessage() + join);
				}
			});
			cachedCoins.stream().forEach(c -> {
				PriceData priceData = prices.get(c.getSymbol());
				if (priceData != null) {
					c.setToUSD(priceData.getPrice());
				}
			});
			lastCallTimeStamp = System.currentTimeMillis();
		}
	}
}
