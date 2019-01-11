/**
 * Created By Anamika Pandey
 */
package com.myapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.myapp.modal.BrandMaster;

public interface BrandRepository extends CrudRepository<BrandMaster, Long> {

}
