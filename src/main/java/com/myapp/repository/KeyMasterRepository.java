package com.myapp.repository;

import org.springframework.data.repository.CrudRepository;


import com.myapp.modal.KeyMaster;

public interface KeyMasterRepository extends CrudRepository<KeyMaster, Long>{

	KeyMaster findByName(String name);

}
