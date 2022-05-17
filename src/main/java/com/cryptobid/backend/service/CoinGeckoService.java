package com.cryptobid.backend.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinGeckoService {
	private final RestTemplate restTemplate;

	public CoinGeckoService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public float getCryptoValue(String id) {
		String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + id + "&vs_currencies=usd";
		String coinDetailsString = this.restTemplate.getForObject(url, String.class);
		float currentCryptoValue;
		try {
			if (coinDetailsString != null) {
				JsonObject jsonObject = new JsonParser().parse(coinDetailsString).getAsJsonObject();
				currentCryptoValue = Float.parseFloat(jsonObject.get(id).getAsJsonObject().get("usd").getAsString());
			} else {
				currentCryptoValue = 0;
			}
		} catch (Exception exception) {
			currentCryptoValue = 0;
		}
		return currentCryptoValue;
	}
}
