package ca.sheridancollege.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.extras.Logger;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
	
	@Autowired @Lazy DatabaseAccess database;
	private Logger log = new Logger();
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		ca.sheridancollege.beans.User accountUser = database.findUserAccount(username);
		
		if (accountUser == null) {
			try {
				log.print("User not found: " + username);
			} catch (IOException e) {e.printStackTrace();}
			
			throw new UsernameNotFoundException
				("User " + username + " was not found in the database");
		}
		
		List<String> roleNames = database.getRolesById(accountUser.getUserId());
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for(String role: roleNames) {
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		UserDetails userDetails = (UserDetails)new User(
				accountUser.getUsername(), accountUser.getPassword(), grantList);
		
		return userDetails;
	}

}
