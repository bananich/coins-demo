/*
* Copyright (C) 2009-2020 SAP SE or an SAP affiliate company. All rights reserved
*/
package org.nana.exam.coins.service;

import org.springframework.stereotype.Service;

/**
 * @author i068309
 *
 */
@Service
public class StateService {

	private int lastCoinRowId;

	public int getLastCoinId() {
		return lastCoinRowId;
	}

	public void setLastCoinId() {
		this.lastCoinRowId++;
	}
}
