package com.remitter.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.remitter.entity.Remitter;


public interface RemitterService {
	//public RE<remitter& account> loginRemitter(String username,string password);
	public Remitter createRemitter(Remitter remitter) throws ParseException;
	public Remitter getRemitterByEmailPassword(String email,String password);
	public Boolean updateRemitterMaxLimitById(int customerId);
	public List<Remitter> getAllRemitters();
	public Optional<Remitter> getRemitterById(int customerId);
}
