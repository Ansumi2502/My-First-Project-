package com.myapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myapp.modal.BillDetails;
import com.myapp.modal.InvoiceReceiptDetails;
import com.myapp.modal.ItemDistributionDetails;
import com.myapp.modal.ReceiptMaster;
import com.myapp.repository.InvoiceReceiptRepository;
import com.myapp.repository.ReceiptRepository;

@Service
@Transactional
public class InvoiceReceiptService {
	
	private final InvoiceReceiptRepository invoiceReceiptRepository;
	
	public InvoiceReceiptService(InvoiceReceiptRepository invoiceReceiptRepository) {
		this.invoiceReceiptRepository = invoiceReceiptRepository;
	}
	
	public void saveInvoiceReceipt(InvoiceReceiptDetails invoiceReceiptDetails) {
		invoiceReceiptRepository.save(invoiceReceiptDetails);
	}
	
	public List<InvoiceReceiptDetails> showAllInvoiceReceiptDetails(){
		List<InvoiceReceiptDetails> invoiceReceiptDetails=new ArrayList<InvoiceReceiptDetails>();
		for(InvoiceReceiptDetails invoiceReceiptRepositoryObj:invoiceReceiptRepository.findAll()) {
			invoiceReceiptDetails.add(invoiceReceiptRepositoryObj);
		}
		return invoiceReceiptDetails;
	}
	
	public List<InvoiceReceiptDetails> getAllInvoiceReceiptDetails(Long receiptMasterId){
		List<InvoiceReceiptDetails> invoiceReceiptDetails=new ArrayList<InvoiceReceiptDetails>();
		for(InvoiceReceiptDetails invoiceReceiptRepositoryObj:invoiceReceiptRepository.findAll()) {
			if(receiptMasterId==invoiceReceiptRepositoryObj.getReceiptMaster().getId()) {
				invoiceReceiptDetails.add(invoiceReceiptRepositoryObj);
			}
		}
		return invoiceReceiptDetails;
	}
	
	public Collection<InvoiceReceiptDetails> showAllReceiptBillDetailsWise(Collection<BillDetails> billDetailsColls) {
		List<InvoiceReceiptDetails> invoiceReceiptDetailsColl=new ArrayList<InvoiceReceiptDetails>();
		for(BillDetails billDetails2:billDetailsColls) {
			invoiceReceiptDetailsColl.addAll(invoiceReceiptRepository.findByBillDetails(billDetails2));
		}
		return invoiceReceiptDetailsColl;
	}

	public InvoiceReceiptDetails editInvoiceReceiptIds(Long id) {
		InvoiceReceiptDetails invoiceReceiptDetail=invoiceReceiptRepository.findById(id).get();
		return invoiceReceiptDetail;
	}
	
	public void saveInvoiceReceiptDetails(InvoiceReceiptDetails invoiceReceiptDetails) {
		invoiceReceiptRepository.save(invoiceReceiptDetails);
	}
	
	public Collection<InvoiceReceiptDetails> findInvoiceReceiptDetails(BillDetails billDetails2) {
		List<InvoiceReceiptDetails> invoiceReceiptDetailsColl=new ArrayList<InvoiceReceiptDetails>();
		invoiceReceiptDetailsColl.addAll(invoiceReceiptRepository.findByBillDetails(billDetails2));
		return invoiceReceiptDetailsColl;
	}
	
}
