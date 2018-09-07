package com.javasampleapproach.couchbase.Customer.service;

import com.javasampleapproach.couchbase.Customer.controller.CustomerController;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Customer.model.Customer;
import com.javasampleapproach.couchbase.Customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component("CustomerService")
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = Logger.getLogger( CustomerController.class.getName() );

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        if (customers.size() == 0) {
            throw new NotFoundException("Nessun Customer Trovato");
        }
        StringBuilder listCustomer = new StringBuilder();
        listCustomer.append("Lista Customers:\n");
        for (Customer c : customers) {
            listCustomer.append(c.toString() + "\n");
        }
        LOGGER.info(listCustomer.toString());
        return customers;
    }

    @Override
    public Customer getCustomerByName(String firstName) {
        Customer c = new Customer();
        for (Customer customer : customerRepository.findAll()) {
            if (customer.getFirstName().matches("(.*)" + firstName + "(.*)")) {
                c = customer;
            }
        }
        LOGGER.info("Customer:\n" + c.toString());
        return c;
    }

    @Override
    public Customer getCustomerById(String id) {
        Customer c =  customerRepository.findOne(id);
        if (c == null)
            throw new NotFoundException("Customer con id: " + id + " NON Trovato");
        LOGGER.info("Customer:\n" + c.toString());
        return c;
    }

    @Override
    public Customer addCustomer(Customer c) {
        return customerRepository.save(c);
    }

    @Override
    public Customer updateCustomer(Customer nuovoCustomer, String id) {
        if (this.customerRepository.exists(id)) {
            Customer c = this.getCustomerById(id);
            c.setNumeroRichieste(nuovoCustomer.getNumeroRichieste());
            this.customerRepository.getCouchbaseOperations().update(c);
            LOGGER.info("Customer Aggiornato:\n" + c.toString());
            return c;
        }
        else {
            throw new NotFoundException("Customer NON Aggiornato");
        }
    }

    @Override
    public Customer deleteCustomerById(String id) {
        Customer c = this.getCustomerById(id);
        customerRepository.delete(c);
        LOGGER.info("Customer Eliminato: " + c.toString());
        return c;
    }
}
