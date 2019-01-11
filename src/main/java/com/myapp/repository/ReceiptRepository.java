/**
 * Created By Anamika Pandey
 */
package com.myapp.repository;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.myapp.modal.BillDetails;
import com.myapp.modal.ReceiptMaster;

public interface ReceiptRepository extends CrudRepository<ReceiptMaster, Long> {

	ReceiptMaster findByReceiptNo(String receiptNo);

	ReceiptMaster findByReceiptDate(String receiptDate);
	
	@Query(value = "SELECT max(receipt_seq) from receipt_master", nativeQuery = true)
	long getMaxReceiptSeq();

	
}
