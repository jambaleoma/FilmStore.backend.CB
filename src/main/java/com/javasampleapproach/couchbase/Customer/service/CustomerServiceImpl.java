package com.javasampleapproach.couchbase.Customer.service;

import com.javasampleapproach.couchbase.Customer.model.Customer;
import com.javasampleapproach.couchbase.Customer.repository.CustomerRepository;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

@Component("CustomerService")
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = Logger.getLogger( CustomerServiceImpl.class.getName() );

    // Save the uploaded file to this folder FOR WINDOWS
    // private static String UPLOADED_FOLDER = "C:\\Users\\Enzo\\spindox-workspace\\filmProject.frontend\\src\\assets\\showcase\\images\\customer\\";

    // Save the uploaded file to this folder FOR MAC
    private static String UPLOADED_FOLDER = "/Users/vincenzo/Documents/FilmStore/FilmStore.frontend/src/assets/showcase/images/customer/";

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        if (customers.size() == 0) {
            throw new NotFoundException("Nessun Customer Trovato");
        }
        for (Customer c : customers) {
            c.setPassword("********");
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
            if (customerDaAggiornare.isAvatar() && !nuovoCustomer.isAvatar()) {
                this.deleteCustomerAvatar(customerDaAggiornare.getId());
            }
            customerDaAggiornare.setFirstName(nuovoCustomer.getFirstName());
            customerDaAggiornare.setLastName(nuovoCustomer.getLastName());
            customerDaAggiornare.setDataDiNascita(nuovoCustomer.getDataDiNascita());
            customerDaAggiornare.setSesso(nuovoCustomer.getSesso());
            customerDaAggiornare.setNumeroRichieste(nuovoCustomer.getNumeroRichieste());
            customerDaAggiornare.setValue(nuovoCustomer.getValue());
            customerDaAggiornare.setLabel(nuovoCustomer.getLabel());
            customerDaAggiornare.setCategoriePreferite(nuovoCustomer.getCategoriePreferite());
            customerDaAggiornare.setAdmin(nuovoCustomer.isAdmin());
            customerDaAggiornare.setAvatar(nuovoCustomer.isAvatar());
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
    public Boolean changeCustomerPsw(Customer nuovoCustomer, String id) {
        if (customerRepository.exists(id)) {
            Customer customerDaAggiornare = customerRepository.findOne(id);
            customerDaAggiornare.setPassword(nuovoCustomer.getPassword());
            this.customerRepository.getCouchbaseOperations().update(customerDaAggiornare);
            if (this.customerRepository.findOne(id).getPassword().equals(nuovoCustomer.getPassword())) {
                return true;
            } else {
                return false;
            }
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

    @Override
    public Boolean loginCustomer(Customer loggingCustomer, String psw) {
        boolean login = false;
        Customer c = customerRepository.findOne(loggingCustomer.getId());
        if (c.getPassword().equals(psw)) {
            login = true;
        }
        return login;
    }

    private Boolean deleteCustomerAvatar(String id) {
        if ( id.length() < 1 ) {
            return false;
        } else {
            Path path = Paths.get( UPLOADED_FOLDER + id + ".png" );
            try {
                Files.delete(path);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }
}
