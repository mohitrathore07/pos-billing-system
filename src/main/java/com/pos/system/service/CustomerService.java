package com.pos.system.service;

import com.pos.system.Modal.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer) throws  Exception;
    void deleteCustomer(Long id) throws  Exception;
    Customer getCustomer(Long id) throws  Exception;
    List<Customer> getAllCustomers() throws  Exception;
    List<Customer> searchCustomers(String keyword) throws  Exception;
}
