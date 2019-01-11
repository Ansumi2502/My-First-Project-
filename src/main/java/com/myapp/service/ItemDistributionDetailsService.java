/**
 * Created By Anamika Pandey
 */
package com.myapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myapp.modal.BillDetails;
import com.myapp.modal.DepotMaster;
import com.myapp.modal.ItemDistributionDetails;
import com.myapp.repository.ItemDistributionDetailsRepository;

@Service
@Transactional
public class ItemDistributionDetailsService {

	private final ItemDistributionDetailsRepository itemDistributionDetailsRepository;

	public ItemDistributionDetailsService(ItemDistributionDetailsRepository itemDistributionDetailsRepository) {
		this.itemDistributionDetailsRepository = itemDistributionDetailsRepository;
	}
	
	public void saveItemDistributionDetails(ItemDistributionDetails itemDistributionDetails) {
		itemDistributionDetailsRepository.save(itemDistributionDetails);
	}
	
	public List<ItemDistributionDetails> showAllItemDistributionDetails(){
		List<ItemDistributionDetails> itemDistributionDetails=new ArrayList<ItemDistributionDetails>();
		for(ItemDistributionDetails itemDistributionDetails2:itemDistributionDetailsRepository.findAll()) {
			itemDistributionDetails.add(itemDistributionDetails2);
		}
		return itemDistributionDetails;
	}
	
	public void deleteItemDistributionDetails(Long id) {
		itemDistributionDetailsRepository.deleteById(id);
	}
	
	public ItemDistributionDetails editItemDistributionDetails(Long id) {
		ItemDistributionDetails itemDistributionDetails=itemDistributionDetailsRepository.findById(id).get();
		return itemDistributionDetails;
	}
	
	public List<ItemDistributionDetails> getAllItemDistributionDetails(Long billDetails){
		List<ItemDistributionDetails> itemDistributionDetails=itemDistributionDetailsRepository.findAllByBillDetails(billDetails);
		return itemDistributionDetails;
	}
	
}
