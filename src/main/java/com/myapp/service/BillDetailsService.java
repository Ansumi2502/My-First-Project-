/**
 * Created By Anamika Pandey
 */
package com.myapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Example;
import org.springframework.stereotype.Service;

import com.myapp.modal.ApplnUser;
import com.myapp.modal.BillDetails;
import com.myapp.modal.CustomerMaster;
import com.myapp.modal.DepotMaster;
import com.myapp.repository.BillDetailsRepository;

@Service
@Transactional
public class BillDetailsService{

	private final BillDetailsRepository billDetailsRepository;

	public BillDetailsService(BillDetailsRepository billDetailsRepository) {
		this.billDetailsRepository = billDetailsRepository;
	}
	
	public void saveBillDetails(BillDetails billDetails) {
		billDetailsRepository.save(billDetails);
	}
	
	public List<BillDetails> showAllBillDetails(){
		List<BillDetails> billDetails=new ArrayList<BillDetails>();
		billDetails.addAll(billDetailsRepository.findAllByOrderByIdDesc());
		return billDetails;
	}
	
	public BillDetails getBillDetails(Long id) {
		BillDetails billDetails=billDetailsRepository.findById(id).get();
		return billDetails;
	}

	public Collection<BillDetails> showAllBillDetailsDepotWise(DepotMaster depotMaster) {
		List<BillDetails> billDetails=new ArrayList<BillDetails>();
		billDetails.addAll(billDetailsRepository.findByDepotMasterOrderByIdDesc(depotMaster));
		return billDetails;
	}
	
	public long getMaxInvoiceSeq() {
		long maxNo=billDetailsRepository.getMaxInvoiceSeq();
		return maxNo;
	}

	public BillDetails getBillDetailsByCustomerMasterAndStatus(CustomerMaster customerMaster, String status) {
		BillDetails billDetails=billDetailsRepository.findByCustomerMasterAndStatus(customerMaster,status);
		return billDetails;
	}
	
}
