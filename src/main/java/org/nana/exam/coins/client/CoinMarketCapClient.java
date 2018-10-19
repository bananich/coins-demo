/*
* Copyright (C) 2009-2020 SAP SE or an SAP affiliate company. All rights reserved
*/
package org.nana.exam.coins.client;

import org.nana.exam.coins.dto.CoinMarketResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author i068309
 *
 */
@FeignClient(url = "${coins.api}", name = "Coins-market-api")
public interface CoinMarketCapClient {
	
	@GetMapping(path = "v2/ticker/?limit=1&sort=id&start={start}")
	CoinMarketResponseDTO getCoin(@RequestParam("start") int start);
}
