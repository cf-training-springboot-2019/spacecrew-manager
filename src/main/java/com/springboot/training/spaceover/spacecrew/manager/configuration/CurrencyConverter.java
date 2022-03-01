package com.springboot.training.spaceover.spacecrew.manager.configuration;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
//LT9 Configuration Properties for currency conversion
//LT9.1-Include various sector currencies and application profiles
public class CurrencyConverter {

	public String id;
	public Map<String, Currency> converter;

	public Optional<Currency> getCurrency() {
		return Optional.ofNullable(converter.get(id));
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Currency {

		private String coin;
		private BigDecimal rate;

	}


}