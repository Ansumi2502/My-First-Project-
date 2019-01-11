/**
 * Created By Anamika Pandey
 */
package com.myapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.myapp.modal.DepotMaster;

public interface DepotRepository extends CrudRepository<DepotMaster, Long> {

}
