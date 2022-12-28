package com.remitter.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.remitter.entity.Account;
import com.remitter.entity.CombinedData;
import com.remitter.entity.CombinedData2;
import com.remitter.entity.Remitter;
import com.remitter.exception.RemitterAlreadyExistsException;
import com.remitter.service.AccountService;
import com.remitter.service.RemitterService;


@RestController
@CrossOrigin(value = "http://localhost:4200/")
public class RemitterController {

	@Autowired
	RemitterService remitterService;

	@Autowired
	AccountService accountService;

	@PostMapping("/register")
	public ResponseEntity<CombinedData> createRemitterAPI(@Validated@RequestBody CombinedData2 data)
			throws ParseException {
		CombinedData response =new CombinedData();
		System.out.println("Client is sending this data"+data);
		if (accountService.getAccountByAccountNumber(data.getAccountNumber()).isPresent())
			throw new RemitterAlreadyExistsException(" same account number already present");
		Remitter dataRemitter = new Remitter(data.getName(),data.getEmail(),data.getAddress(),data.getPassword());
		Remitter remitter = remitterService.createRemitter(dataRemitter);
		if (remitter == null)
			throw new NullPointerException("remitter creation failed!");
		Account dataAccount = new Account(data.getAccountNumber(),data.getAccountBalance(),data.getAccountStatus());
		Account account = new Account();
		dataAccount.setRemitter(remitter);
		account = accountService.createAccount(dataAccount);
		if (account == null)
			throw new NullPointerException("account creation failed!");
		response.setRemitter(remitter);
		response.setAccount(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<CombinedData> loginRemitterAPI(@RequestBody Remitter remitter) {
		Remitter remitterDetails = remitterService.getRemitterByEmailPassword(remitter.getEmail(),
				remitter.getPassword());
		if (remitterDetails == null)
			throw new NullPointerException("remitter not found");
		Account accountDetails = accountService.getAccountByRemitterId(remitterDetails.getCustomerId());
		if (accountDetails == null)
			throw new NullPointerException("account data not found ");
		CombinedData data = new CombinedData(remitterDetails, accountDetails);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	@PutMapping("/updateMaxLimitById/{customerId}")
	public ResponseEntity<Boolean> updateRemitterMaxLimitById(@PathVariable int customerId) {
		Boolean isUpdated = false;
		isUpdated = remitterService.updateRemitterMaxLimitById(customerId);
		return ResponseEntity.status(HttpStatus.OK).body(isUpdated);
	}
	
	@PutMapping("/updateBalanceByAccountNumber/{accountNumber}")
	public ResponseEntity<Boolean> updateBalanceByAccountNumber(@PathVariable Long accountNumber,@RequestParam Double amount) {
		Boolean isUpdated = false;
		isUpdated = accountService.debitBalanceByAccountNumber(accountNumber,amount);
		return ResponseEntity.status(HttpStatus.OK).body(isUpdated);
	}
	
	@GetMapping("/remitterBalance/{accountNumber}")
	public ResponseEntity<Double> getRemitterBalanceByAccountNumber(@PathVariable Long accountNumber) {
		Double remitterBalance = accountService.getAccountByAccountNumber(accountNumber).get().getAccountBalance();
		return ResponseEntity.status(HttpStatus.OK).body(remitterBalance);
	}
	
	
}
