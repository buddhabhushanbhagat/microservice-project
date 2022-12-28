package com.remitter.service;

import java.util.Optional;

import com.remitter.entity.Account;

public interface AccountService {
	public Account createAccount(Account account);
	public Account getAccountByRemitterId(int remitterId);
	public Optional<Account> getAccountByAccountNumber(long accountNumber);
	public Boolean debitBalanceByAccountNumber(Long accountNumber, Double account);
}
