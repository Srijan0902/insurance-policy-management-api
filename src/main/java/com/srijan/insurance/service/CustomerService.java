package com.srijan.insurance.service;

import com.srijan.insurance.dto.CustomerRequestDTO;
import com.srijan.insurance.dto.CustomerResponseDTO;
import com.srijan.insurance.entity.Customer;
import com.srijan.insurance.exception.CustomerNotFoundException;
import com.srijan.insurance.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository,
                           PasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    public Customer saveCustomer(CustomerRequestDTO customerRequestDTO) {

        Customer customer = new Customer();

        customer.setName(customerRequestDTO.getName());
        customer.setEmail(customerRequestDTO.getEmail());

        // BCrypt Password
        customer.setPassword(
                passwordEncoder.encode(customerRequestDTO.getPassword())
        );

        // Default Role
        if (customerRequestDTO.getRole() == null ||
                customerRequestDTO.getRole().isBlank()) {

            customer.setRole("USER");

        } else {

            customer.setRole(customerRequestDTO.getRole());
        }

        customer.setPhoneNumber(customerRequestDTO.getPhoneNumber());
        customer.setAddress(customerRequestDTO.getAddress());

        return customerRepository.save(customer);
    }

    // READ ALL
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // READ BY ID
    public CustomerResponseDTO getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with id " + id);
        }

        CustomerResponseDTO dto = new CustomerResponseDTO();

        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());

        return dto;
    }

    // GET BY EMAIL
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // GET BY NAME
    public Customer getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    // UPDATE
    public Customer updateCustomer(Long id,
                                   Customer updatedCustomer) {

        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with id " + id);
        }

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        customer.setAddress(updatedCustomer.getAddress());

        return customerRepository.save(customer);
    }

    // DELETE
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // PAGINATION
    public Page<Customer> getCustomers(int page, int size) {
        return customerRepository.findAll(PageRequest.of(page, size));
    }

    // SORTING
    public List<Customer> getCustomersSorted(String field) {
        return customerRepository.findAll(
                Sort.by(Sort.Direction.ASC, field)
        );
    }

    // PAGINATION + SORTING
    public Page<Customer> getCustomers(int page,
                                       int size,
                                       String field) {

        return customerRepository.findAll(
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.ASC, field)
                )
        );
    }
}