package com.remitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.remitter.entity.Remitter;

@Repository
public interface RemitterRepository extends JpaRepository<Remitter, Integer>{
	public Remitter findByEmailAndPassword(String email,String password);
	
//	@Query(value = "select * from remitter where email=:email",nativeQuery = true)
//	public Optional<Remitter> findByEmail(@Param("email") String email);

	public Remitter findByMaxLimit(int limit);

//	public Remitter findByAccountNumber(Long accountNumber);
	
//	@Query(value="select * from remitter_db.remitter where remitter.email like '?1'",nativeQuery = true)
//    public List<Remitter> findByEmail(String keyword);
	
	//public Remitter findByEmailAndPassword(String Email,String Password);
}
