/**
 * Created By Anamika Pandey
 */
package com.myapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myapp.modal.ApplnUser;
import com.myapp.modal.DepotMaster;
import com.myapp.repository.ApplnUserRepository;

@Service
@Transactional
public class ApplnUserService {
	
	private final ApplnUserRepository applnUserRepository;
	
	public ApplnUserService(ApplnUserRepository applnUserRepository) {
		this.applnUserRepository = applnUserRepository;
	}
	public void saveAppnUser(ApplnUser applnUser) {
		applnUserRepository.save(applnUser);
	}
	
	public List<ApplnUser> showAllApplnUsers(){
		List<ApplnUser> applnUsers=new ArrayList<ApplnUser>();
		for(ApplnUser applnUser:applnUserRepository.findAll()) {
			applnUsers.add(applnUser);
		}
		return applnUsers;
	}
	
	public void deleteApplnUser(Long id) {
		applnUserRepository.deleteById(id);
	}
	
	public ApplnUser editApplnUser(Long id) {
		ApplnUser applnUser=applnUserRepository.findById(id).get();
		return applnUser;
	}
	
	public ApplnUser findByUserNameAndPassword(String userId,String password) {
		return applnUserRepository.findByUserIdAndPassword(userId, password);
	}
	public ApplnUser findByUserName(String userId) {
		return applnUserRepository.findByUserId(userId);
	}
	
	public List<ApplnUser> showAllApplnUsersDepotWise(DepotMaster depotMaster){
		List<ApplnUser> applnUsers=new ArrayList<ApplnUser>();
		applnUsers.addAll(applnUserRepository.findByDepotMaster(depotMaster));
		return applnUsers;
	}
}
