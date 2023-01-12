package com.remitter.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.remitter.entity.Account;
import com.remitter.entity.CombinedData;
import com.remitter.entity.CombinedData2;
import com.remitter.entity.Remitter;
import com.remitter.exception.RemitterAlreadyExistsException;
import com.remitter.exception.RemitterNotFoundException;
import com.remitter.exception.RemitterUpdateFailedException;
import com.remitter.service.AccountService;
import com.remitter.service.RemitterService;


@RestController
@CrossOrigin(value = "http://localhost:4200/")
@RequestMapping("/remitter")
public class RemitterController {

	@Autowired
	RemitterService remitterService;

	@Autowired
	AccountService accountService;

	@PostMapping("/register")
	public ResponseEntity<CombinedData> createRemitterAPI(@Validated@RequestBody CombinedData2 data)
			throws ParseException, AccountNotFoundException {
		CombinedData response =new CombinedData();
		System.out.println("Client is sending this data"+data);
		if (accountService.getAccountByAccountNumber(data.getAccountNumber()).isPresent())
			throw new RemitterAlreadyExistsException(" same account number already present");
		Remitter dataRemitter = new Remitter(data.getName(),data.getEmail(),data.getAddress(),data.getPassword(),data.getContactNo());
		Remitter remitter = remitterService.createRemitter(dataRemitter);
		if (remitter == null)
			throw new RemitterNotFoundException("remitter creation failed!");
		Account dataAccount = new Account(data.getAccountNumber(),data.getAccountBalance(),data.getAccountStatus());
		Account account = new Account();
		dataAccount.setRemitter(remitter);
		account = accountService.createAccount(dataAccount);
		if (account == null)
			throw new AccountNotFoundException("account creation failed!");
		response.setRemitter(remitter);
		response.setAccount(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<CombinedData> loginRemitterAPI(@RequestBody Remitter remitter) throws AccountNotFoundException {
		Remitter remitterDetails = remitterService.getRemitterByEmailPassword(remitter.getEmail(),
				remitter.getPassword());
		if (remitterDetails == null)
			throw new RemitterNotFoundException("remitter not found");
		Account accountDetails = accountService.getAccountByRemitterId(remitterDetails.getCustomerId());
		if (accountDetails == null)
			throw new AccountNotFoundException("account data not found ");
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
		if(accountService.getAccountByAccountNumber(accountNumber).isEmpty())
			throw new RemitterNotFoundException("Remitter account not found for Id "+accountNumber); 
		Double remitterBalance = accountService.getAccountByAccountNumber(accountNumber).get().getAccountBalance();
		return ResponseEntity.status(HttpStatus.OK).body(remitterBalance);
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Remitter> getRemitterByAccountNumber(@PathVariable Long accountNumber) {
		Remitter remitter = accountService.getRemitterByAccountNumber(accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body(remitter);
	}
	
	@GetMapping("/account/{accountNumber}")
	public ResponseEntity<Account> getRemitterAndAccountByAccountNumber(@PathVariable Long accountNumber) {
		Account account = accountService.getRemitterAndAccountByAccountNumber(accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body(account);
	}
	
	@GetMapping("")
	public ResponseEntity<List<CombinedData>> getAllRemitters() {
		List<CombinedData> remitterAccountList = new ArrayList<>();
		List<Remitter> remitterList = remitterService.getAllRemitters();
		remitterList.forEach(remitter->{
			Account account = accountService.getAccountByRemitterId(remitter.getCustomerId());
			CombinedData remitterAccount = new CombinedData(remitter,account);
			remitterAccountList.add(remitterAccount);
		});
		if(remitterAccountList.isEmpty())
			throw new RemitterNotFoundException("All remitters account not found");
		return ResponseEntity.status(HttpStatus.OK).body(remitterAccountList);
	}	
	
	@PutMapping("/{customerId}")
	public ResponseEntity<CombinedData> updateRemitterAndAccount(@Validated @RequestBody CombinedData2 data,@PathVariable int customerId)
			throws ParseException, AccountNotFoundException {
		CombinedData response =new CombinedData();
		if (!accountService.getAccountByAccountNumber(data.getAccountNumber()).isPresent())
			throw new RemitterNotFoundException(" Remitter account not found for acc no "+data.getAccountNumber());
		if (!remitterService.getRemitterById(customerId).isPresent())
			throw new RemitterNotFoundException(" Remitter account not found for Id "+customerId);
		
		Remitter remitter = remitterService.getRemitterById(customerId).get();
		remitter.setName(data.getName());
		remitter.setEmail(data.getEmail());
		remitter.setAddress(data.getAddress());
		remitter.setPassword(data.getPassword());
		remitter.setContactNo(data.getContactNo());
		
		Remitter updatedRemitter = remitterService.createRemitter(remitter);
		if (updatedRemitter == null)
			throw new RemitterUpdateFailedException("remitter update failed");
		
		Account account = accountService.getAccountByAccountNumber(data.getAccountNumber()).get();
		account.setAccountBalance(data.getAccountBalance());
		account.setAccountStatus(data.getAccountStatus());
		account.setRemitter(updatedRemitter);
		Account updatedAccount = accountService.createAccount(account);
		if (updatedAccount == null)
			throw new RemitterUpdateFailedException(" remitter account update failed");
		response.setRemitter(updatedRemitter);
		response.setAccount(updatedAccount);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/account/{accountNumber}")
	public ResponseEntity<Boolean> deleteRemitterAccountByAccountNumber(@PathVariable long accountNumber) {
		Boolean result = false;
		Account account = accountService.getRemitterAndAccountByAccountNumber(accountNumber);
		System.out.println(account);
		if(account == null)
			throw new RemitterNotFoundException("remitter not presnt while on delete acc no "+accountNumber);
		result = accountService.deleteRemitterAccountByAccountNumber(account.getAccountNumber());
		return  ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
}
