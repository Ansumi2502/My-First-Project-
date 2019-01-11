/**
 * Created By Anamika Pandey
 */
package com.myapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myapp.modal.BillDetails;
import com.myapp.modal.BrandMaster;
import com.myapp.modal.CustomerMaster;
import com.myapp.modal.DepotMaster;
import com.myapp.modal.ReceiptMaster;
import com.myapp.repository.ReceiptRepository;

@Service
@Transactional
public class ReceiptService {
	 
	private final ReceiptRepository receiptRepository;

	public ReceiptService(ReceiptRepository receiptRepository) {
		this.receiptRepository = receiptRepository;
	}
	
	public void saveReceipt(ReceiptMaster receiptMaster) {
		receiptRepository.save(receiptMaster);
	}
	
	public List<ReceiptMaster> showAllReceipt(){
		List<ReceiptMaster> receipts=new ArrayList<ReceiptMaster>();
		for(ReceiptMaster receipt:receiptRepository.findAll()) {
			receipts.add(receipt);
		}
		return receipts;
	}
	
	public void deleteReceipt(Long id) {
		receiptRepository.deleteById(id);
	}
	
	public ReceiptMaster editReceitp(Long id) {
		ReceiptMaster receiptMaster=receiptRepository.findById(id).get();
		return receiptMaster;
	}
	
	public ReceiptMaster findReceitByReceiptNo(String receiptNo) {
		ReceiptMaster receiptMaster=receiptRepository.findByReceiptNo(receiptNo);
		return receiptMaster;
	}
	
	public ReceiptMaster findReceiptDateWise(String receiptDate) {
		ReceiptMaster receiptMaster=receiptRepository.findByReceiptDate(receiptDate);
		return receiptMaster;
	}
	
	public long getMaxReceiptSeq() {
		long maxNo=receiptRepository.getMaxReceiptSeq();
		return maxNo;
	}
	
}
