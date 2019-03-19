package com.javasampleapproach.couchbase.Customer.controller;

import com.javasampleapproach.couchbase.Customer.model.Customer;
import com.javasampleapproach.couchbase.Customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/rest/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Save the uploaded file to this folder FOR WINDOWS
    // private static String UPLOADED_FOLDER = "C:\\Users\\Enzo\\spindox-workspace\\filmProject.frontend\\src\\assets\\showcase\\images\\customer\\";

    // Save the uploaded file to this folder FOR MAC
    private static String UPLOADED_FOLDER = "/Users/vincenzo/Documents/FilmStore/FilmStore.frontend/src/assets/showcase/images/customer/";

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllCustomers() {
        try {
            List<Customer> customers = this.customerService.getAllCustomers();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Customers", "--- OK --- Lista Customers Trovata Con Successo").body(customers);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/byName/{name}")
    private ResponseEntity getCustomerByName(@PathVariable String name) {
        try {
            Customer customers = this.customerService.getCustomerByName(name);
            return ResponseEntity.status(HttpStatus.OK).header("Lista Customers per Nome", "--- OK --- Lista Customers per Nome Trovata Con Successo").body(customers);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    private ResponseEntity getCustomerById(@PathVariable String id) {
        try {
            Customer customerById = this.customerService.getCustomerById(id);
            return ResponseEntity.status(HttpStatus.OK).header("Ricerca Customer", "--- OK --- Customer Trovato Con Successo").body(customerById);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertCustomer")
    public ResponseEntity addCustomer(@RequestBody Customer c) {
        try {
            Customer customerSalvato = customerService.addCustomer(c);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Customer", "--- OK --- Customer Creato Con Successo").body(getAllCustomers().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/upDateCustomer/{id}")
    private ResponseEntity updateCustomer (@RequestBody Customer nuovoCustomer, @PathVariable String id) {
        try {
            Customer customerAggiornato = customerService.updateCustomer(nuovoCustomer, id);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Customer", "--- OK --- Customer Aggiornato Con Successo").body(getAllCustomers().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/changeCustomerPsw/{id}")
    private ResponseEntity changeCustomerPsw (@RequestBody Customer nuovoCustomer, @PathVariable String id) {
        try {
            Boolean aggiornato = customerService.changeCustomerPsw(nuovoCustomer, id);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Customer", "--- OK --- Customer Aggiornato Con Successo").body(getAllCustomers().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteCustomerById/{id}")
    private ResponseEntity deleteCustomerById(@PathVariable String id) {
        try {
            customerService.deleteCustomerById(id);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Customer", "--- OK --- Customer Eliminato Con Successo").body("Il Customer con Id: " + id + " è stato Eliminato con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/loginCustomer/{psw}")
    private ResponseEntity loginCustomer(@RequestBody Customer loggingCustomer, @PathVariable String psw) {
        try {
            Boolean login = customerService.loginCustomer(loggingCustomer, psw);
            return ResponseEntity.status(HttpStatus.OK).header("Customer Loggato con Successo", "--- OK --- Il Customers è stato Loggato con Successo").body(login);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/avatar/saveCustomerImage/{customerId}")
    private ResponseEntity saveCustomerImage(@RequestParam("customerAvatar") MultipartFile file,  @PathVariable String customerId) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Avatar Customer", "Non è stato trovato nessun File da caricare").body("Errore");
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get( UPLOADED_FOLDER + customerId + ".png" );
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).header("Avatar Customer", "Avatar Customer Aggirnato con Successo").body("OK");
    }

}