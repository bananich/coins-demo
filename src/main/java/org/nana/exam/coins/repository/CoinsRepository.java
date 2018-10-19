/*
* Copyright (C) 2009-2020 SAP SE or an SAP affiliate company. All rights reserved
*/
package org.nana.exam.coins.repository;

import org.nana.exam.coins.dto.CoinDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author i068309
 *
 */
public interface CoinsRepository extends JpaRepository<CoinDTO, String>{
}
