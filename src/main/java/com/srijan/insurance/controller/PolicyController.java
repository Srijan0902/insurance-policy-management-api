package com.srijan.insurance.controller;

import com.srijan.insurance.entity.Policy;
import com.srijan.insurance.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // Create Policy
    @PostMapping
    public Policy savePolicy(@Valid @RequestBody Policy policy) {
        return policyService.savePolicy(policy);
    }

    // Get All Policies
    @GetMapping
    public List<Policy> getAllPolicies() {
        return policyService.getAllPolicies();
    }

    // Get Policy By ID
    @GetMapping("/{id}")
    public Policy getPolicyById(@PathVariable Long id) {
        return policyService.getPolicyById(id);
    }
    @PutMapping("/{id}")
    public Policy updatePolicy(@PathVariable Long id, @Valid @RequestBody Policy policy){
        return policyService.updatePolicy(id, policy);
    }
    @DeleteMapping("/{id}")
    public void deletePolicy(@PathVariable Long id){
        policyService.deletePolicy(id);
    }
    @GetMapping("/status/{status}")
    public List<Policy> getPoliciesByStatus(@PathVariable String status) {
        return policyService.getPoliciesByStatus(status);
    }
    @GetMapping("/type/{policyType}")
    public List<Policy> getPoliciesByType(@PathVariable String policyType) {
        return policyService.getPoliciesByType(policyType);
    }
    @GetMapping("/premium/{premium}")
    public List<Policy> getPoliciesByPremium(@PathVariable Double premium) {
        return policyService.getPoliciesByPremium(premium);
    }
}