/*
* Copyright (C) 2009-2020 SAP SE or an SAP affiliate company. All rights reserved
*/
package org.nana.exam.coins.service;

import java.util.List;

import javax.transaction.Transactional;

import org.nana.exam.coins.client.CoinMarketCapClient;
import org.nana.exam.coins.dto.CoinDTO;
import org.nana.exam.coins.dto.CoinMarketResponseDTO;
import org.nana.exam.coins.repository.CoinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author i068309
 *
 */
@Service
@Transactional
public class CoinsService {

	@Autowired
	private CoinsRepository coinsRepo;

	@Autowired
	private CoinMarketCapClient coinsClient;

	@Autowired
	private StateService stateService;

	public void addCoin() {
		CoinMarketResponseDTO coin = coinsClient.getCoin(stateService.getLastCoinId());
		coin.getData().values().forEach(o -> coinsRepo.save(CoinDTO.builder()
				.label(o.getName()).price(o.getQuotes().getUsd().getPrice()).build()));
		stateService.setLastCoinId();
	}

	public List<CoinDTO> fetchAll() {
		return coinsRepo.findAll();
	}
}
