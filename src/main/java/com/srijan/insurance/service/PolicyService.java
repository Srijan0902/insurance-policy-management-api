package com.srijan.insurance.service;

import com.srijan.insurance.entity.Customer;
import com.srijan.insurance.entity.Policy;
import com.srijan.insurance.repository.CustomerRepository;
import com.srijan.insurance.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final CustomerRepository customerRepository;

    public PolicyService(PolicyRepository policyRepository,
                         CustomerRepository customerRepository) {
        this.policyRepository = policyRepository;
        this.customerRepository = customerRepository;
    }

    // Save Policy
    public Policy savePolicy(Policy policy) {

        Long customerId = policy.getCustomer().getId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        policy.setCustomer(customer);

        return policyRepository.save(policy);
    }

    // Get All Policies
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    // Get Policy By ID
    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id).orElse(null);
    }
    public Policy updatePolicy(Long id, Policy updatedPolicy) {

        Policy existingPolicy = policyRepository.findById(id)
                .orElse(null);

        if (existingPolicy == null) {
            return null;
        }

        existingPolicy.setPolicyNumber(updatedPolicy.getPolicyNumber());
        existingPolicy.setPolicyType(updatedPolicy.getPolicyType());
        existingPolicy.setPremium(updatedPolicy.getPremium());
        existingPolicy.setCoverageAmount(updatedPolicy.getCoverageAmount());
        existingPolicy.setStartDate(updatedPolicy.getStartDate());
        existingPolicy.setEndDate(updatedPolicy.getEndDate());
        existingPolicy.setStatus(updatedPolicy.getStatus());

        return policyRepository.save(existingPolicy);
    }
    public void deletePolicy(Long id){
        policyRepository.deleteById(id);
    }
    public List<Policy> getPoliciesByStatus(String status) {
        return policyRepository.findByStatus(status);
    }
    public List<Policy> getPoliciesByType(String policyType) {
        return policyRepository.findByPolicyType(policyType);
    }
    public List<Policy> getPoliciesByPremium(Double premium) {
        return policyRepository.findByPremiumGreaterThan(premium);
    }
}