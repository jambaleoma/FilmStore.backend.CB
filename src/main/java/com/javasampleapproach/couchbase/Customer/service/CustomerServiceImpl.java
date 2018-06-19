package com.javasampleapproach.couchbase.Customer.service;

import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Customer.model.Customer;
import com.javasampleapproach.couchbase.Customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("CustomerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        if (customers.size() == 0) {
            throw new NotFoundException("Nessun Customer Trovato");
        }
        return customers;
    }

    @Override
    public List<Customer> getAllCustomersByName(String firstName) {
        List<Customer> customers = new ArrayList<>();
        for (Customer c : customerRepository.findAll()) {
            if (c.getFirstName().matches("(.*)" + firstName + "(.*)"))
                customers.add(c);
        }
        if (customers.size() == 0) {
            throw new NotFoundException("Nessun Customer con Nome: " + firstName + " è stato Trovato");
        }
        return customers;
    }

    @Override
    public Customer getCustomerById(String id) {
        Customer c =  customerRepository.findOne(id);
        if (c == null)
            throw new NotFoundException("Customer con id: " + id + " NON Trovato");
        return c;
    }

    @Override
    public Customer addCustomer(Customer c) {
        return customerRepository.save(c);
    }

    @Override
    public Customer updateCustomer(Customer nuovoCustomer, String id) {
        Customer customerDaAggiornare =  this.getCustomerById(id);
        return customerRepository.save(nuovoCustomer);
    }

    @Override
    public Customer deleteCustomerById(String id) {
        Customer c = this.getCustomerById(id);
        customerRepository.delete(c);
        return c;
    }
}
