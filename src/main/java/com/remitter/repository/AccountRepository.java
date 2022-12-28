package com.remitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.remitter.entity.Account;
import com.remitter.entity.Remitter;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
	
	public Account findByRemitterCustomerId(int customerId); 
	public Account findByAccountNumber(Long accountNumber);
}
