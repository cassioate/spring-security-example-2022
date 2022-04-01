package com.tessaro.springsecurity.controller;

import com.tessaro.springsecurity.domain.Accounts;
import com.tessaro.springsecurity.domain.Customer;
import com.tessaro.springsecurity.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	@Autowired
	private AccountsRepository accountsRepository;

	@GetMapping("/myAccount")
	@PreAuthorize("hasRole('USER')")
	public String getTestROLE() {
		return "TESTE role pass";
	}

	@PostMapping("/myAccount")
	@PreAuthorize("hasRole('USER')")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		Accounts accounts = accountsRepository.findByCustomerId(customer.getId());
		if (accounts != null ) {
			return accounts;
		}else {
			return null;
		}
	}

}
