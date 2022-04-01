package com.tessaro.springsecurity.controller;

import com.tessaro.springsecurity.domain.Cards;
import com.tessaro.springsecurity.domain.Customer;
import com.tessaro.springsecurity.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {
	
	@Autowired
	private CardsRepository cardsRepository;

	@GetMapping("/myCards")
	@PreAuthorize("hasRole('ADMIN')")
	public String getTestROLE() {
		return "TESTE role pass";
	}

	@PostMapping("/myCards")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Cards> getCardDetails(@RequestBody Customer customer) {
		List<Cards> cards = cardsRepository.findByCustomerId(customer.getId());
		if (cards != null ) {
			return cards;
		}else {
			return null;
		}
	}

}
