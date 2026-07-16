package com.srijan.insurance.controller;

import com.srijan.insurance.dto.CustomerRequestDTO;
import com.srijan.insurance.dto.CustomerResponseDTO;
import com.srijan.insurance.entity.Customer;
import com.srijan.insurance.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(
        name = "Customer Management",
        description = "APIs for managing customers"
)
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // CREATE
    @Operation(
            summary = "Create a new customer",
            description = "Creates a new customer and stores it in the database"
    )

    @PostMapping
    public Customer saveCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {

        return customerService.saveCustomer(customerRequestDTO);
    }

    // GET ALL
    @Operation(
            summary = "Get all customers",
            description = "Returns the complete list of customers"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<Customer> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    // GET BY ID

    @Operation(
            summary = "Get customer by ID",
            description = "Returns customer details using customer ID"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {

        return customerService.getCustomerById(id);
    }

    // GET BY EMAIL
    @Operation(
            summary = "Get customer by Email",
            description = "Returns customer details using email address"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/email/{email}")
    public Customer getCustomerByEmail(@PathVariable String email) {

        return customerService.getCustomerByEmail(email);
    }

    // GET BY NAME
    @Operation(
            summary = "Get customer by Name",
            description = "Returns customer details using customer name"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/name/{name}")
    public Customer getCustomerByName(@PathVariable String name) {

        return customerService.getCustomerByName(name);
    }

    // UPDATE
    @Operation(
            summary = "Update customer",
            description = "Updates customer details using customer ID"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer updatedCustomer) {

        return customerService.updateCustomer(id, updatedCustomer);
    }

    // DELETE
    @Operation(
            summary = "Delete customer",
            description = "Deletes customer using customer ID"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);
    }

    // PAGINATION
    @Operation(
            summary = "Pagination",
            description = "Returns customers page by page"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/page")
    public Page<Customer> getCustomers(

            @RequestParam int page,
            @RequestParam int size) {

        return customerService.getCustomers(page, size);
    }

    // SORTING
    @Operation(
            summary = "Sorting",
            description = "Returns customers sorted by any field"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/sort")
    public List<Customer> getCustomersSorted(

            @RequestParam String field) {

        return customerService.getCustomersSorted(field);
    }

    // PAGINATION + SORTING
    @Operation(
            summary = "Pagination with Sorting",
            description = "Returns paginated and sorted customers"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/page-sort")
    public Page<Customer> getCustomersPageAndSort(

            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field) {

        return customerService.getCustomers(
                page,
                size,
                field
        );
    }

}