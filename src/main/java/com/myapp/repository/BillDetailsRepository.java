/**
 * Created By Anamika Pandey
 */
package com.myapp.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.myapp.modal.BillDetails;
import com.myapp.modal.CustomerMaster;
import com.myapp.modal.DepotMaster;

public interface BillDetailsRepository extends CrudRepository<BillDetails, Long> {

	Collection<? extends BillDetails> findByDepotMaster(DepotMaster depotMaster);
	
	public List<BillDetails> findAllByOrderByIdDesc();

	Collection<? extends BillDetails> findByDepotMasterOrderByIdDesc(DepotMaster depotMaster);
	
	@Query(value = "SELECT max(invoice_seq) from bill_details", nativeQuery = true)
	long getMaxInvoiceSeq();

	BillDetails findByCustomerMasterAndStatus(CustomerMaster customerMaster, String status);
}
