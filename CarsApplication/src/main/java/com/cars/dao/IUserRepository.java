package com.cars.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cars.beans.Customer;
import com.cars.beans.User;

public interface IUserRepository extends JpaRepository<User, Long> {
	
	/*To Retrive the data of user from database using Id & Password*/
	@Query("Select u from User u where u.userId=?1 and u.password=?2")
	Optional<User> SignIn(long userId,String password);
	
	/*To Retrive the of user data from database using Id*/
	@Query("Select u from User u where u.userId=?1")
	Optional<User> SignOut(long userIdno);
	
	/*To Retrive the data of user from database using Id & Password*/
	@Query("Select u from User u where u.userId=?1 and u.password=?2")
	Optional<User> Changepassword(long userId,String password);
	
	/*To Retrive the data of customer from database using Id */
	@Query("Select c from Customer c where c.userId=?1")
	Optional<Customer> detailscustomer(long userId);

}
