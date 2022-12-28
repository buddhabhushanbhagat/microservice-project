package com.remitter.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remitter.entity.Remitter;
import com.remitter.repository.RemitterRepository;
import com.remitter.util.ApplicationConstants;

import jakarta.transaction.Transactional;

@Service
public class RemitterServiceImpl implements RemitterService {

	@Autowired
	RemitterRepository remitterRepo;
	
	public Remitter createRemitter(Remitter remitter) throws ParseException {
		remitter.setMaxLimit(ApplicationConstants.BENEFICIARY_REGISTRATION_MAX_LIMIT);
		return remitterRepo.save(remitter);
	}

	@Override
	public Remitter getRemitterByEmailPassword(String email,String password) {
		Remitter remitter = null;
		remitter = remitterRepo.findByEmailAndPassword(email,password);
		return remitter;
	}

	@Override
	public Boolean updateRemitterMaxLimitById(int customerId) {
		// TODO Auto-generated method stub
		Remitter remitter = remitterRepo.findById(customerId).get();
		if(remitter.getMaxLimit() > 0) {
			remitter.setMaxLimit(remitter.getMaxLimit()-1);
			remitterRepo.save(remitter);
			return true;
		}
		return false;
	}

}
