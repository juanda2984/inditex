package com.inditex.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.inditex.entities.Prices;

public interface PricesService {
	List<Prices> getPricesAll();

	Optional<Prices> getPricesConditional(String apply, Long productId, Long brandId) throws ParseException;

	Prices savePrices(Prices prices);
}
