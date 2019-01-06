package org.nana.exam.coins.client;

import java.util.Map;

import org.nana.exam.coins.external.dto.CoinsData;
import org.nana.exam.coins.external.dto.PriceData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${external.api}", name = "cryptocompare")
public interface ExternalApi {

	@GetMapping(path = "/data/all/coinlist")
	public CoinsData listAllCoins();

	@GetMapping(path = "/data/pricemulti?fsyms={fsyms}&tsyms=USD")
	public Map<String, PriceData> getPrices(@RequestParam("fsyms") String fsyms);
}
