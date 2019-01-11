/**
 * Created By Anamika Pandey
 */
package com.myapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myapp.modal.ApplnUser;
import com.myapp.modal.DepotMaster;

public interface ApplnUserRepository extends CrudRepository<ApplnUser, Long> {
	
	public ApplnUser findByUserIdAndPassword(String userId,String password);

	public ApplnUser findByUserId(String userId);
	
	public List<ApplnUser> findByDepotMaster(DepotMaster depotMaster);

}
