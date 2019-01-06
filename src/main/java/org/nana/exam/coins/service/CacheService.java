package org.nana.exam.coins.service;

import org.springframework.stereotype.Service;

@Service
public class CacheService {
	private Integer cacheTtl;

	public Integer getCacheTtl() {
		if(cacheTtl == null) {
			return 15000;
		}
		return cacheTtl;
	}

	public void setCacheTtl(Integer cacheTtl) {
		this.cacheTtl = cacheTtl;
	}
}
