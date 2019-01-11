/**
 * Created By Anamika Pandey
 */
package com.myapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myapp.modal.ItemDistributionDetails;

public interface ItemDistributionDetailsRepository extends CrudRepository<ItemDistributionDetails, Long> {

	List<ItemDistributionDetails> findAllByBillDetails(Long id);

	List<ItemDistributionDetails> findAllByBillDetailsOrderByIdDesc(Long billDetails);

}
