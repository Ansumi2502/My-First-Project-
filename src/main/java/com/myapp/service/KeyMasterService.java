/**
 * Created By Anamika Pandey
 */
package com.myapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myapp.modal.ApplnUser;
import com.myapp.modal.KeyMaster;
import com.myapp.repository.KeyMasterRepository;

@Service
@Transactional
public class KeyMasterService {
	
	private final KeyMasterRepository keyMasterRepository;

	public KeyMasterService(KeyMasterRepository keyMasterRepository) {
		this.keyMasterRepository = keyMasterRepository;
	}

	public void saveKeyMaster(KeyMaster keyMaster) {
		keyMasterRepository.save(keyMaster);
	}
	
	public List<KeyMaster> showAllKeyMasters(){
		List<KeyMaster> keyMasters=new ArrayList<KeyMaster>();
		for(KeyMaster keyMaster:keyMasterRepository.findAll()) {
			keyMasters.add(keyMaster);
		}
		return keyMasters;
	}
	
	public void deleteKeyMaster(Long id) {
		keyMasterRepository.deleteById(id);
	}
	
	public KeyMaster editKeyMaster(Long id) {
		KeyMaster keyMaster=keyMasterRepository.findById(id).get();
		return keyMaster;
	}
	
	public KeyMaster findByName(String name) {
		return keyMasterRepository.findByName(name);
	}
	
	
}
