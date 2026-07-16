package com.srijan.insurance.repository;

import com.srijan.insurance.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByStatus(String status);
    List<Policy> findByPolicyType(String policyType);
    List<Policy> findByPremiumGreaterThan(Double premium);
}
