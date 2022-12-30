package com.remitter.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remitter.entity.Account;
import com.remitter.entity.Remitter;
import com.remitter.repository.AccountRepository;

//import jakarta.transaction.Transactional;

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

	@Override
	public Remitter getRemitterByAccountNumber(Long accountNumber) {
		// TODO Auto-generated method stub
		return getAccountByAccountNumber(accountNumber).get().getRemitter();
	}

	@Override
	public Account getRemitterAndAccountByAccountNumber(Long accountNumber) {
		// TODO Auto-generated method stub
		return getAccountByAccountNumber(accountNumber).get();
	}

	@Override
	public Boolean deleteRemitterAccountByAccountNumber(long accountNumber) {
		// TODO Auto-generated method stub
		 accountRepo.deleteById(accountNumber);
		 return true;
	}

	

}
