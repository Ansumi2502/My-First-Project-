/**
 * Created By Anamika Pandey
 */
package com.myapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myapp.modal.BrandMaster;
import com.myapp.modal.DepotMaster;
import com.myapp.repository.DepotRepository;

@Service
@Transactional
public class DepotService {
	
	private final DepotRepository depotRepository;

	public DepotService(DepotRepository depotRepository) {
		this.depotRepository = depotRepository;
	}
	
	public void saveDepot(DepotMaster depotMaster) {
		depotRepository.save(depotMaster);
	}
	
	public List<DepotMaster> showAllDepot(){
		List<DepotMaster> depotMasters=new ArrayList<DepotMaster>();
		for(DepotMaster depotMaster:depotRepository.findAll()) {
			depotMasters.add(depotMaster);
		}
		return depotMasters;
	}
	
	public void deleteDepot(Long id) {
		depotRepository.deleteById(id);
	}
	
	public DepotMaster editDepot(Long id) {
		DepotMaster depotMaster=depotRepository.findById(id).get();
		return depotMaster;
	}

	public DepotMaster findById(long id) {
		DepotMaster depotMaster=depotRepository.findById(id).get();
		return depotMaster;
	}
}
