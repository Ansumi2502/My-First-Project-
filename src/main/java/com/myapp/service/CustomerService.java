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
import com.myapp.modal.BrandMaster;
import com.myapp.modal.CustomerMaster;
import com.myapp.modal.DepotMaster;
import com.myapp.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public void saveCustomer(CustomerMaster customerMaster) {
		customerRepository.save(customerMaster);
	}
	
	public List<CustomerMaster> showAllCustomer(){
		List<CustomerMaster> customers=new ArrayList<CustomerMaster>();
		for(CustomerMaster customer:customerRepository.findAll()) {
			customers.add(customer);
		}
		return customers;
	}
	
	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}
	
	public CustomerMaster editCustomer(Long id) {
		CustomerMaster customerMaster=customerRepository.findById(id).get();
		return customerMaster;
	}

	public List<CustomerMaster> showAllCustomerByDepotWise(DepotMaster depotMaster) {
		List<CustomerMaster> customerMasters=new ArrayList<CustomerMaster>();
		customerMasters.addAll(customerRepository.findByDepotMaster(depotMaster));
		return customerMasters;
	}
	
	public CustomerMaster findById(long id) {
		CustomerMaster customerMaster=customerRepository.findById(id).get();
		return customerMaster;
	}
	
}
