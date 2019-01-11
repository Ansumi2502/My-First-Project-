package com.myapp.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myapp.modal.BillDetails;
import com.myapp.modal.InvoiceReceiptDetails;
import com.myapp.modal.ItemDistributionDetails;
import com.myapp.modal.ReceiptMaster;

public interface InvoiceReceiptRepository extends CrudRepository<InvoiceReceiptDetails, Long> {
	Collection<? extends InvoiceReceiptDetails> findByBillDetails(BillDetails billDetails2);
	Collection<? extends InvoiceReceiptDetails> findByReceiptMaster(ReceiptMaster receiptMaster);
	List<InvoiceReceiptDetails> findAllByReceiptMaster(Long receiptMasterId);
}
