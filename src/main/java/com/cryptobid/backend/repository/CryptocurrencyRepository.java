package com.cryptobid.backend.repository;

import com.cryptobid.backend.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Integer> {

}
