package com.inditex.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.entities.Prices;
import com.inditex.service.PricesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@RestController
@RequestMapping("/prices")
@Tag(name = "Inditex API", description = "API de Precios que refleja el precio final (pvp) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas")
public class PricesController {

	private final PricesService pricesService;

	@Autowired
	public PricesController(PricesService pricesService) {
		this.pricesService = pricesService;
	}

	@GetMapping("/")
	@Operation(summary = "Obtener todos los precios", description = "Obtiene una lista de todos los precios")
	@ApiResponse(responseCode = "200", description = "Lista de precios", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Prices.class))))
	public ResponseEntity<List<Prices>> getPricesAll() {
		try {
			List<Prices> prices = pricesService.getPricesAll();
			if (prices.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(prices, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{apply}/{productId}/{brandId}")
	@Operation(summary = "Obtener un prices por condiciones",
	description = "Obtener un pricio por fecha de aplicación, identificador de producto, identificador de cadena")
	@ApiResponse(responseCode = "200", description = "Precio consultado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Prices.class)) })
	public ResponseEntity<Prices> getPricesConditional(@PathVariable String apply, @PathVariable Long productId,
			@PathVariable Long brandId) {
		try {
			Optional<Prices> prices = pricesService.getPricesConditional(apply, productId, brandId);
			if (prices.isPresent()) {
				return new ResponseEntity<>(prices.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/")
	@Operation(summary = "Crear Precio", description = "Crear Precio")
	@ApiResponse(responseCode = "200", description = "Crear Precio", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Prices.class)) })
	public ResponseEntity<Prices> savePrices(@RequestBody Prices prices) {
		try {
			Prices pricesResult = pricesService.savePrices(prices);
			if (pricesResult != null) {
				return new ResponseEntity<>(pricesResult, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
