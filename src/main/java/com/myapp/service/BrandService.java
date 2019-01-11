/**
 * Created By Anamika Pandey
 */
package com.myapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myapp.modal.BrandMaster;
import com.myapp.modal.CustomerMaster;
import com.myapp.repository.BrandRepository;

@Service
@Transactional
public class BrandService {
	
	private final BrandRepository brandRepository;

	public BrandService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}
	
	public void saveBrand(BrandMaster brandMaster) {
		brandRepository.save(brandMaster);
	}
	
	public List<BrandMaster> showAllBrand(){
		List<BrandMaster> brands=new ArrayList<BrandMaster>();
		for(BrandMaster brand:brandRepository.findAll()) {
			brands.add(brand);
		}
		return brands;
	}
	
	public void deleteBrand(Long id) {
		brandRepository.deleteById(id);
	}
	
	public BrandMaster editBrand(Long id) {
		BrandMaster brandMaster=brandRepository.findById(id).get();
		return brandMaster;
	}

	public BrandMaster findById(long id) {
		BrandMaster brandMaster=brandRepository.findById(id).get();
		return brandMaster;
	}
	
}
