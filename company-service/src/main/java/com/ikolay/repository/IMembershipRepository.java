package com.ikolay.repository;

import com.ikolay.repository.entity.Company;
import com.ikolay.repository.entity.Membership;
import com.ikolay.repository.enums.EMembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMembershipRepository extends JpaRepository<Membership,Long> {


    List<Membership> findByStatusOrderByPriceAsc(EMembershipStatus eMembershipStatus);
}
