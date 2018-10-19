/*
* Copyright (C) 2009-2020 SAP SE or an SAP affiliate company. All rights reserved
*/
package org.nana.exam.coins.controller;

import java.util.List;

import org.nana.exam.coins.dto.CoinDTO;
import org.nana.exam.coins.service.CoinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author i068309
 *
 */
@RestController
public class CoinsController {

	@Autowired
	private CoinsService coinsService;
	
	@GetMapping("/")
	public List<CoinDTO> get() {
		return coinsService.fetchAll();
	}
	
	@GetMapping("/add")
	public void addCoin(){
		coinsService.addCoin();
	}
}
