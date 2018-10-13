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

    private static final Logger LOGGER = Logger.getLogger( CustomerServiceImpl.class.getName() );

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        if (customers.size() == 0) {
            throw new NotFoundException("Nessun Customer Trovato");
        }
        StringBuilder listCustomer = new StringBuilder();
        listCustomer.append("\nLista Utenti:\n");
        for (Customer c : customers) {
            listCustomer.append("Nome: " + c.getFirstName() + " Cognome: " + c.getLastName() + "\n");
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
        StringBuilder listCustomer = new StringBuilder();
        listCustomer.append("\nUtente:\n");
            listCustomer.append("Nome: " + c.getFirstName() + " Cognome: " + c.getLastName() + "\n");
        LOGGER.info(listCustomer.toString());
        return c;
    }

    @Override
    public Customer getCustomerById(String id) {
        Customer c =  customerRepository.findOne(id);
        if (c == null)
            throw new NotFoundException("Customer con id: " + id + " NON Trovato");
        StringBuilder listCustomer = new StringBuilder();
        listCustomer.append("\nUtente:\n");
        listCustomer.append("Nome: " + c.getFirstName() + " Cognome: " + c.getLastName() + "\n");
        LOGGER.info(listCustomer.toString());
        return c;
    }

    @Override
    public Customer addCustomer(Customer c) {
        c.setNumeroRichieste(0);
        return customerRepository.save(c);
    }

    @Override
    public Customer updateCustomer(Customer nuovoCustomer, String id) {
        if (customerRepository.exists(id)) {
            Customer customerDaAggiornare = customerRepository.findOne(id);
            customerDaAggiornare.setFirstName(nuovoCustomer.getFirstName());
            customerDaAggiornare.setLastName(nuovoCustomer.getLastName());
            customerDaAggiornare.setPassword(nuovoCustomer.getPassword());
            customerDaAggiornare.setNumeroRichieste(nuovoCustomer.getNumeroRichieste());
            customerDaAggiornare.setValue(nuovoCustomer.getValue());
            customerDaAggiornare.setLabel(nuovoCustomer.getLabel());
            customerDaAggiornare.setAdmin(nuovoCustomer.isAdmin());
            this.customerRepository.getCouchbaseOperations().update(customerDaAggiornare);
            StringBuilder listCustomer = new StringBuilder();
            listCustomer.append("\nUtente Aggiornato:\n");
            listCustomer.append("Nome: " + customerDaAggiornare.getFirstName() + " Cognome: " + customerDaAggiornare.getLastName() + "\n");
            LOGGER.info(listCustomer.toString());
            return customerDaAggiornare;
        }
        else {
            throw new NotFoundException("Customer NON Aggiornato");
        }
    }

    @Override
    public Customer deleteCustomerById(String id) {
        Customer c = this.getCustomerById(id);
        customerRepository.delete(c);
        StringBuilder listCustomer = new StringBuilder();
        listCustomer.append("\nUtente Eliminato:\n");
        listCustomer.append("Nome: " + c.getFirstName() + " Cognome: " + c.getLastName() + "\n");
        LOGGER.info(listCustomer.toString());
        return c;
    }
}
