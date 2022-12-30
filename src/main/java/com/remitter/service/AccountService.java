package com.remitter.service;

import java.util.Optional;

import com.remitter.entity.Account;
import com.remitter.entity.Remitter;

public interface AccountService {
	public Account createAccount(Account account);
	public Account getAccountByRemitterId(int remitterId);
	public Optional<Account> getAccountByAccountNumber(long accountNumber);
	public Boolean debitBalanceByAccountNumber(Long accountNumber, Double account);
	public Remitter getRemitterByAccountNumber(Long accountNumber);
	public Account getRemitterAndAccountByAccountNumber(Long accountNumber);
	public Boolean deleteRemitterAccountByAccountNumber(long accountNumber);
}
