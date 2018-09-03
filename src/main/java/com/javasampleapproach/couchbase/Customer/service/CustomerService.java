package com.javasampleapproach.couchbase.Customer.service;


import com.javasampleapproach.couchbase.Customer.model.Customer;

import java.util.List;

public interface CustomerService {

        List<Customer> getAllCustomers();
        Customer getCustomerByName(String name);
        Customer getCustomerById(String id);
        Customer addCustomer(Customer c);
        Customer updateCustomer (Customer nuovoCustomer, String id);
        Customer deleteCustomerById(String id);
}

