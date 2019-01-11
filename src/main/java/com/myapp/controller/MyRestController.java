/**
 * Created By Anamika Pandey
 */
package com.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.modal.ApplnUser;
import com.myapp.service.ApplnUserService;

@RestController
public class MyRestController {
	@Autowired
	private ApplnUserService applnUserService;
	
	@GetMapping("/")
	public String Hello() {
		return "This is Home Page";
	}
	
	@GetMapping("/saveUser")
	public String saveUser(@RequestParam String userId,@RequestParam String password,
			@RequestParam String email,@RequestParam String userType,@RequestParam String status,
			@RequestParam String mobileNo,@RequestParam String name) {
		ApplnUser applnUser = new ApplnUser(password, userId, name, email, userType, status,mobileNo);
		applnUserService.saveAppnUser(applnUser);
		return "User is saved";
	}
}
