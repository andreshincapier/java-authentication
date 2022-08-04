package com.bolsadeideas.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.backend.apirest.models.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findByUsername(String username);
	
	@Query("select u from User u where u.username=?1")
	public User findByUsername2(String username);

}
