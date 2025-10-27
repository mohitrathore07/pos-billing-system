package com.pos.system.service.impl;

import com.pos.system.Modal.Customer;
import com.pos.system.repository.CustomerRepository;
import com.pos.system.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private  final CustomerRepository customerRepository;
    @Override
    public Customer createCustomer(Customer customer) {
            return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new Exception("no customer found with id: "+id
        ));
        existingCustomer.setFullName(customer.getFullName());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setEmail(customer.getEmail());
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new Exception("no customer found with id: "+id
        ));

        customerRepository.delete(existingCustomer);
    }

    @Override
    public Customer getCustomer(Long id) throws Exception {
        return customerRepository.findById(id).orElseThrow(() -> new Exception("no customer found with id: "+id
        ));
    }

    @Override
    public List<Customer> getAllCustomers() throws Exception {
        return  customerRepository.findAll();
    }

    @Override
    public List<Customer> searchCustomers(String keyword) throws Exception {
        return customerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }
}
