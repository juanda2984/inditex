package com.inditex.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Prices {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pricesId;
	private Long brandId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date endDate;
	private int priceList;
	private Long productId;
	private int priority;
	private BigDecimal price;
	private String curr;

	public Prices() {
		super();
	}

	public Prices(Long pricesId, Long brandId, Date startDate, Date endDate, int priceList, Long productId,
			int priority, BigDecimal price, String curr) {
		super();
		this.pricesId = pricesId;
		this.brandId = brandId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceList = priceList;
		this.productId = productId;
		this.priority = priority;
		this.price = price;
		this.curr = curr;
	}
}
