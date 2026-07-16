package com.srijan.insurance.security;

import com.srijan.insurance.entity.Customer;
import com.srijan.insurance.repository.CustomerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer == null) {
            throw new UsernameNotFoundException(
                    "User not found with email: " + email
            );
        }

        return new User(

                customer.getEmail(),

                customer.getPassword(),

                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + customer.getRole()
                        )
                )

        );
    }
}
