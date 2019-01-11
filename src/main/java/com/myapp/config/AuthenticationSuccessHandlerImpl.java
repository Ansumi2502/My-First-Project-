/**
 * Created By Anamika Pandey
 */
package com.myapp.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myapp.modal.ApplnUser;
import com.myapp.service.ApplnUserService;

@Service
public class AuthenticationSuccessHandlerImpl implements UserDetailsService {
	@Autowired
	private ApplnUserService applnUserService;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplnUser applnUser = applnUserService.findByUserName(username);
		GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(applnUser.getUserType());
		User user= new User(applnUser.getUserId(),applnUser.getPassword(),Arrays.asList(grantedAuthority));
		UserDetails userDetails=(UserDetails)user;
		return userDetails;
	}

}
