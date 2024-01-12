
package com.drinkme.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.drinkme.admin.user.UserRepository;
import com.drinkme.common.entity.User;

public class DrinkmeUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		
		if(user != null) {
			return new DrinkmeUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Impossibile trovare utente con email: " + email);
	}

}
