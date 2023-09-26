package com.ikolay.repository;

import com.ikolay.repository.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICompanyRepository extends JpaRepository<Company,Long> {

    Boolean existsByTaxNo(String taxNo);

    List<Company> findByIdIn(List<Long> companyIds);
}
