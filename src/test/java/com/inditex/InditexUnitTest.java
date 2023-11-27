package com.inditex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.entities.Prices;
import com.inditex.repository.PricesRepository;
import com.inditex.service.PricesService;

@SpringBootTest
public class InditexUnitTest {
	@Autowired
	private PricesRepository pricesRepository;

	@Autowired
	private PricesService pricesService;

	@Test
	@DisplayName("Tabla Prices con salida de datos")
	public void testPriceEntity() throws ParseException, JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd-hh.mm.ss");
		ObjectMapper mapper = new ObjectMapper();

		Prices prices = Prices.builder().pricesId(null).brandId(1L)
				.startDate(new Timestamp(formatter.parse("2020-06-15-16.00.00").getTime()))
				.endDate(new Timestamp(formatter.parse("2020-12-31-23.59.59").getTime())).priceList(4).productId(35455L)
				.priority(1).price(new BigDecimal("38.95")).curr("EUR").build();
		Prices entitiy = pricesService.getPricesConditional("2020-08-30-20.00.00.00", 35455L, 1L).get();
		entitiy.setPricesId(null);
		assertEquals(mapper.writeValueAsString(prices), mapper.writeValueAsString(entitiy));
	}

	@Test
	@DisplayName("Tabla Prices sin salida de datos")
	public void testPriceEntityEmptyResult() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd-hh.mm.ss");
		Optional<Prices> entitiy = pricesRepository.getPriceByProductIdAndBrandIdAndDateApply(35455L, 1L,
				formatter.parse("2020-04-10-00.00.00.00"));
		assertEquals(Optional.empty(), entitiy);
	}

	@Test
	@DisplayName("Tabla Prices guardar o actualizar un registro")
	public void testSavePrices() throws ParseException, JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd-hh.mm.ss");
		ObjectMapper mapper = new ObjectMapper();

		Prices prices = Prices.builder().brandId(1L)
				.startDate(new Timestamp(formatter.parse("2023-09-11-08.00.00").getTime()))
				.endDate(new Timestamp(formatter.parse("2023-09-13-23.59.59").getTime())).priceList(4).productId(35455L)
				.priority(1).price(new BigDecimal("45.96")).curr("EUR").build();

		prices = pricesService.savePrices(prices);

		Optional<Prices> entitiy = pricesRepository.getPriceByProductIdAndBrandIdAndDateApply(35455L, 1L,
				formatter.parse("2023-09-12-20.00.00.00"));

		assertEquals(mapper.writeValueAsString(prices), mapper.writeValueAsString(entitiy.get()));
	}
}
