package com.ikolay.repository;

import com.ikolay.repository.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends JpaRepository<Company,Long> {


    String findByCompanyName(Long id);

    Boolean existsByTaxNo(String taxNo);
}
