package com.cars.service;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cars.advices.ResourceNotFoundException;
import com.cars.beans.Customer;
import com.cars.beans.User;
import com.cars.dao.IUserRepository;

@Service
public class UserService implements IUserService {
	
	@Autowired	/* To Connect with User Repository and dependency Injection  */
	IUserRepository userrepository;
	
	/* Regex condition to check password */
	String regex = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%&])"
            + "(?=\\S+$).{8,20}$";
	Pattern p = Pattern.compile(regex);
	
	/* The sign in is a login Method -> It verifies the data and return appropriate statement or data */
	@Override /* It is child class method overriding the parent class method */
	public Optional<Customer> signIn(long userId,String password)throws Exception 
	{
	   Supplier<Exception> s1 = ()->new ResourceNotFoundException("Register or invalid id and Password.. ");
	   Supplier<Exception> s2 = ()->new ResourceNotFoundException("There are no customer details please fill the deatils in customer using this user id.");
	   Optional<Customer> customerdata;
	   userrepository.SignIn(userId,password).orElseThrow(s1);
	   customerdata=Optional.of(userrepository.detailscustomer(userId).orElseThrow(s2));
	   return customerdata;
	}
	
	/* The ChangeUserPassword is a update Method -> It verifies and validates the details given and update it */
	@Override /* It is child class method overriding the parent class method */
	public User changeUserPassword(long userId,String password,String newpassword,String confirm_password) throws Exception  
	{	
	    String np=newpassword;
		Supplier<Exception> s1 = ()->new ResourceNotFoundException("User id or password is not correct");
		User updatePassword=userrepository.Changepassword(userId,password).orElseThrow(s1);
		if(password.isEmpty() || np.isEmpty() || confirm_password.isEmpty())
		{
    		throw new ResourceNotFoundException("Null values are not accepted");
		}
		if(!np.equals(password)) /* Password check with old password */
		 {
		  if(p.matcher(np).matches() && p.matcher(confirm_password).matches()) /* Password criteria validation */
		    {
		      if(np.equals(confirm_password)) /* Password check fo new & confirm password */
		    	{
		    	  updatePassword.setPassword(np);
		    	  userrepository.save(updatePassword);
		    	}
		      else
		    	{
		    	 throw new ResourceNotFoundException("new password and confirm password are not same");
		    	}
		    }
		    else 
		    {
		     throw new ResourceNotFoundException("Password dosen't match the criteria..it should have alteast one upper,lower,number,special characters like @#$%& and min size of 8 and max of 20"); 
		    }
		 }
		 else
		 {
			throw new ResourceNotFoundException("New password shouldn't be same as old password..");
		 }
		
			return updatePassword;	
	}
	
	/* The sign out method -> It will check data is present or not gives the return statement */
	@Override /* It is child class method overriding the parent class method */
	public User signOut(long userId) throws Exception 
	{
	   Supplier<Exception> s1 = ()->new ResourceNotFoundException("Invalid id for SignOut");
	   User signOutUser=((Optional<User>) userrepository.SignOut(userId)).orElseThrow(s1);
	   return signOutUser;
	}
	
	/* The Create user method -> It verifies the data and add the user */
	@Override /* It is child class method overriding the parent class method */
	public User createUser(User user) throws ResourceNotFoundException
	{
	   long id=user.getUserId();
	   String password=user.getPassword();
	   Optional<User> addUser=userrepository.findById(id);
	   if(addUser.isPresent())
	   {
		 throw new ResourceNotFoundException(id +" is already present in the database");
	   }
	   else if(p.matcher(password).matches()) /* Password criteria validation */
	   {
		  userrepository.save(user);
	   }
	   else
	   {
		  throw new ResourceNotFoundException("Password dosen't match the criteria..it should have alteast one upper,lower,number,special characters like @#$%& and min size of 8 and max of 20"); 
	   }
	   return user;
	}


}
