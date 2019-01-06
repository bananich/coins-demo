package org.nana.exam.coins.controller;

import java.util.List;
import java.util.Optional;

import org.nana.exam.coins.dto.CoinExamDTO;
import org.nana.exam.coins.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoinsController {

	@Autowired
	private ExternalApiService externalApiService;

	@GetMapping("/coins/{symbol}")
	public CoinExamDTO getCoin(@PathVariable String symbol) {
		return externalApiService.getCoin(symbol);
	}

	@GetMapping("/coins")
	public List<CoinExamDTO> getCoins(@RequestParam(name = "algorithm", required = false) Optional<String> algorithm,
			@RequestParam(name = "symbol", required = false) Optional<List<String>> symbols) {
		return externalApiService.getCoinNames(algorithm, symbols);
	}
}
