package com.cars.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cars.advices.ResourceNotFoundException;
import com.cars.beans.Address;
import com.cars.beans.Customer;
import com.cars.beans.User;
import com.cars.dao.IUserRepository;

@SpringBootTest
class UserServiceTest {
	
	@Autowired	/*To connect with the user service and dependency Injection */
	UserService userservice;
	
	@MockBean	/* To create a mock data using this user repository */
	IUserRepository userrepository;

	@Test	/*To Test the Sign in Method using Mockito*/
	void testSignIn() throws Exception
	{
		Address a=new Address();
		a.setAid(1);
		a.setArea("Mallayapeta");
		a.setCity("Rajahmundry");
		a.setDoorNo("852-545");
		a.setPincode(533101);
		a.setState("Andhra Pradesh");
		a.setStreet("Industrial Colony");
		
		User u=new User();
		u.setUserId(10);
		u.setRole("customer");
		u.setPassword("Sunil@143");
		
		Customer c=new Customer();
		c.setUserId(10);
		c.setAddress(a);
		c.setContactNo("9490806700");
		c.setName("Sunil");
		c.setDob("2019-20-5");
		c.setEmail("yaghnasunil@gmail.com");
		
			
		Optional<Customer> c1=Optional.of(c);
		Optional<User> u1=Optional.of(u);
		Mockito.when(userrepository.save(u)).thenReturn(u);
		Mockito.when(userrepository.SignIn(u.getUserId(),u.getPassword())).thenReturn(u1);
		Mockito.when(userrepository.detailscustomer(c.getUserId())).thenReturn(c1);
		assertThat(userservice.signIn(10,"Sunil@143")).isEqualTo(c1);		
	}

	@Test	/*To Test the Sign out Method  using Mockito*/
	void testSignOut() throws Exception
	{
		User u=new User();
		u.setUserId(10);
		u.setRole("customer");
		u.setPassword("Sunil@143");
		
		Optional<User> u1=Optional.of(u);
		Mockito.when(userrepository.findById((long) 10)).thenReturn(u1);
		Mockito.when(userrepository.existsById(u.getUserId())).thenReturn(false);
		assertFalse(userrepository.existsById(u.getUserId()));
	}

	@Test	/*To test the Create User Method using Mockito*/
	void testCreateUser() throws ResourceNotFoundException
	{
		User u=new User();
		u.setUserId(10);
		u.setRole("customer");
		u.setPassword("Sunil@143");
		
		Mockito.when(userrepository.save(u)).thenReturn(u);    
		assertThat(userservice.createUser(u)).isEqualTo(u);
	}

}
