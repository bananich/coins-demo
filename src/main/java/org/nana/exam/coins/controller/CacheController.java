package org.nana.exam.coins.controller;

import org.nana.exam.coins.dto.CacheDTO;
import org.nana.exam.coins.dto.CacheResponseDTO;
import org.nana.exam.coins.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
	@Autowired
	private CacheService cacheService;

	@PostMapping("/cachettl")
	public CacheResponseDTO changeTtl(@RequestBody CacheDTO cacheDTO) {
		CacheResponseDTO cacheResponseDTO = CacheResponseDTO.builder().newValue(cacheDTO.getTtl())
				.oldValue(cacheService.getCacheTtl()).build();
		cacheService.setCacheTtl(cacheDTO.getTtl());
		return cacheResponseDTO;
	}

}
