package com.cryptobid.backend.service;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Cryptocurrency;
import com.cryptobid.backend.repository.CryptocurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@Service
public class CryptocurrencyService {

	private final static Logger log = LoggerFactory.getLogger(CryptocurrencyService.class);
	private final CryptocurrencyRepository cryptocurrencyRepository;

	public CryptocurrencyService(CryptocurrencyRepository cryptocurrencyRepository) {
		this.cryptocurrencyRepository = cryptocurrencyRepository;
		if (cryptocurrencyRepository.findAll().isEmpty()) {
			loadCryptocurrencies();
		}
	}

	public void loadCryptocurrencies() {
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			InputStream inputStream = classLoader.getResourceAsStream("CryptoCurrency.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				String symbol = values[0];
				String name = values[1];
				int rank = Integer.parseInt(values[2]);
				cryptocurrencyRepository.save(new Cryptocurrency(symbol, name, rank));
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Retrieves all the {@link Cryptocurrency} objects
	 *
	 * @return {@link List} of {@link Cryptocurrency} objects
	 */
	public List<Cryptocurrency> getAllCryptocurrencies() {
		return cryptocurrencyRepository.findAll();
	}

	/**
	 * Retrieves the {@link Cryptocurrency} filtered from {@code id}
	 *
	 * @param id which is the id of the filtering {@link Cryptocurrency}
	 * @return {@link Cryptocurrency}
	 * @throws ResourceNotFoundException if the requesting {@link Cryptocurrency} doesn't exist
	 */
	public Cryptocurrency getCryptocurrencyById(int id) throws ResourceNotFoundException {
		Optional<Cryptocurrency> cryptocurrency = cryptocurrencyRepository.findById(id);
		if (cryptocurrency.isEmpty()) {
			String msg = "Error, Cryptocurrency by id: " + id + " doesn't exist.";
			log.error(msg);
			throw new ResourceNotFoundException(msg);
		}
		return cryptocurrency.get();
	}
}
