package com.remitter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
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

import com.remitter.entity.Remitter;
import com.remitter.repository.RemitterRepository;

@ExtendWith(MockitoExtension.class)
class RemitterServiceImplTest {
	@InjectMocks
	private RemitterServiceImpl remitterService;

	@Mock
	private RemitterRepository remitterRepository;

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
	void testCreateRemitter() throws ParseException {
		Remitter remitter = new Remitter(3,"bhushan","bhu@gmail.com",Math.abs(new Random().nextLong()),"fdsfs","bhushan",4,new Date(),new Date());
		 Mockito.when(remitterRepository.save(remitter)).thenReturn(remitter);
		 Remitter actual=remitterService.createRemitter(remitter);
		    assertEquals(remitter, actual);
	}

	@Test
	void testGetRemitterByEmailPassword() {
		Remitter remitter = new Remitter(3,"bhushan","bhu@gmail.com",Math.abs(new Random().nextLong()),"fdsfs","bhushan",4,new Date(),new Date());
		Mockito.when(remitterRepository.findByEmailAndPassword("bhu@gmail.com","bhushan")).thenReturn(remitter);
		 Remitter actual=remitterService.getRemitterByEmailPassword("bhu@gmail.com","bhushan");
		 assertEquals(remitter,actual);
	}
	
	@Test
	void updateRemitterMaxLimitById() {
		Remitter remitter = new Remitter(3,"bhushan","bhu@gmail.com",Math.abs(new Random().nextLong()),"fdsfs","bhushan",4,new Date(),new Date());
		Boolean expected = true;
		Boolean actual = true;
		Optional<Remitter> optionalRemitter = Optional.of(remitter);
		Mockito.when(remitterRepository.findById(Mockito.anyInt())).thenReturn(optionalRemitter);
		Mockito.when(remitterRepository.save(remitter)).thenReturn(remitter);
		if(remitter.getMaxLimit() > 0) {
			remitter.setMaxLimit(remitter.getMaxLimit()-1);
		}
		actual=remitterService.updateRemitterMaxLimitById(remitter.getCustomerId());
		 assertEquals(expected,actual);
	}

}
