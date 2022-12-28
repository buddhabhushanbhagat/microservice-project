package com.remitter.service;

import java.text.ParseException;

import org.springframework.stereotype.Service;

import com.remitter.entity.Account;
import com.remitter.entity.Remitter;


public interface RemitterService {
	//public RE<remitter& account> loginRemitter(String username,string password);
	public Remitter createRemitter(Remitter remitter) throws ParseException;
	public Remitter getRemitterByEmailPassword(String email,String password);
	public Boolean updateRemitterMaxLimitById(int customerId);
}
