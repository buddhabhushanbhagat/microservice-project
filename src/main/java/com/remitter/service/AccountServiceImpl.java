package com.remitter.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remitter.entity.Account;
import com.remitter.repository.AccountRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepo;
	
	@Override
	@Transactional
	public Account createAccount(Account account) {
		// TODO Auto-generated method stub
		return accountRepo.save(account);
	}

	@Override
	@Transactional
	public Account getAccountByRemitterId(int remitterId) {
		// TODO Auto-generated method stub
//		@EntityGraph(value = "Review.comments", type = EntityGraphType.FETCH)
		return accountRepo.findByRemitterCustomerId(remitterId);
	}

	@Override
	public Optional<Account> getAccountByAccountNumber(long accountNumber) {
		// TODO Auto-generated method stub
		return accountRepo.findById(accountNumber);
	}

	@Override
	public Boolean debitBalanceByAccountNumber(Long accountNumber, Double amount) {
		// TODO Auto-generated method stub
		Account accountDetails = getAccountByAccountNumber(accountNumber).get();
		if (accountDetails.getAccountBalance() > amount) {
			accountDetails.setAccountBalance(accountDetails.getAccountBalance()-amount);
			accountRepo.save(accountDetails);
			return true;
		}
		return false;
	}

}
