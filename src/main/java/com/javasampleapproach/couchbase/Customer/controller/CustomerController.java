package com.javasampleapproach.couchbase.Customer.controller;

import com.javasampleapproach.couchbase.Customer.model.Customer;
import com.javasampleapproach.couchbase.Customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/all")
    private ResponseEntity getAllCustomers() {
        try {
            List<Customer> customers = this.customerService.getAllCustomers();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Customers", "--- OK --- Lista Customers Trovata Con Successo").body(customers);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/byName/{name}")
    private ResponseEntity getAllCustomersByName(@PathVariable String name) {
        try {
            List<Customer> customers = this.customerService.getAllCustomersByName(name);
            return ResponseEntity.status(HttpStatus.OK).header("Lista Customers per Nome", "--- OK --- Lista Customers per Nome Trovata Con Successo").body(customers);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/{id}")
    private ResponseEntity getCustomerById(@PathVariable String id) {
        try {
            Customer customerById = this.customerService.getCustomerById(id);
            return ResponseEntity.status(HttpStatus.FOUND).header("Ricerca Customer", "--- OK --- Customer Trovato Con Successo").body(customerById);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/insertCustomer")
    public ResponseEntity addCustomer(@RequestBody Customer c) {
        try {
            Customer customerSalvato = customerService.addCustomer(c);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Customer", "--- OK --- Customer Creato Con Successo").body(getAllCustomers().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping(value = "/upDateCustomer/{id}")
    private ResponseEntity updateCustomer (@RequestBody Customer NuovoCustomer, @PathVariable String id) {
        try {
            Customer customerAggiornato = customerService.updateCustomer(NuovoCustomer, id);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Customer", "--- OK --- Customer Aggiornato Con Successo").body(customerAggiornato.toString());
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(value = "/deleteCustomerById/{id}")
    private ResponseEntity deleteCustomerById(@PathVariable String id) {
        try {
            customerService.deleteCustomerById(id);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Customer", "--- OK --- Customer Eliminato Con Successo").body("Il Customer con Id: " + id + " è stato Eliminato con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

}