package com.cryptobid.backend.controller.admin;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Cryptocurrency;
import com.cryptobid.backend.service.CryptocurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cryptocurrency")
public class CryptocurrencyController {

    private final CryptocurrencyService cryptocurrencyService;

    public CryptocurrencyController(CryptocurrencyService cryptocurrencyService) {
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cryptocurrency> getAllCryptocurrencies() {
        return cryptocurrencyService.getAllCryptocurrencies();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cryptocurrency getCryptocurrencyById(@PathVariable int id) throws ResourceNotFoundException {
        return cryptocurrencyService.getCryptocurrencyById(id);
    }

}
