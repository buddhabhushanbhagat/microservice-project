package com.remitter.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.remitter.entity.Account;
import com.remitter.entity.Remitter;
import com.remitter.repository.AccountRepository;
import com.remitter.repository.RemitterRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
	@InjectMocks
	private AccountServiceImpl accountService;

	@Mock
	private AccountRepository accountRepository;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateAccount() {
		Remitter remitter = new Remitter(3,"bhushan","bhu@gmail.com",Math.abs(new Random().nextLong()),"fdsfs","bhushan",4,new Date(),new Date());
		Account account = new Account(Math.abs(new Random().nextLong()),655.90,4,new Date(),new Date(),remitter);
		 Mockito.when(accountRepository.save(account)).thenReturn(account);
		 Account actual=accountService.createAccount(account);
		    assertEquals(account, actual);
	}

	@Test
	void testGetAccountByRemitterId() {
		Remitter remitter = new Remitter(3,"bhushan","bhu@gmail.com",Math.abs(new Random().nextLong()),"fdsfs","bhushan",4,new Date(),new Date());
		Account account = new Account(Math.abs(new Random().nextLong()),655.90,4,new Date(),new Date(),remitter);
		 Mockito.when(accountRepository.findByRemitterCustomerId(3)).thenReturn(account);
		 Account actual=accountService.getAccountByRemitterId(remitter.getCustomerId());
		    assertEquals(account, actual);
	}

	@Test
	void testGetAccountByAccountNumber() {
		Remitter remitter = new Remitter(3,"bhushan","bhu@gmail.com",Math.abs(new Random().nextLong()),"fdsfs","bhushan",4,new Date(),new Date());
		Optional<Account> account = Optional.of(new Account(Math.abs(new Random().nextLong()),655.90,4,new Date(),new Date(),remitter));
		 Mockito.when(accountRepository.findById(account.get().getAccountNumber())).thenReturn(account);
		 Optional<Account> actual=accountService.getAccountByAccountNumber(account.get().getAccountNumber());
		    assertEquals(account, actual);
	}

}
