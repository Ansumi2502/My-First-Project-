/**
 * Created By Anamika Pandey
 */
package com.myapp.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.myapp.modal.CustomerMaster;
import com.myapp.modal.DepotMaster;

public interface CustomerRepository extends CrudRepository<CustomerMaster, Long> {

	public List<CustomerMaster> findByDepotMaster(DepotMaster depotMaster);

}
