package com.javasampleapproach.couchbase.Customer.repository;

import com.javasampleapproach.couchbase.Customer.model.Customer;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "customer")
public interface CustomerRepository extends CouchbasePagingAndSortingRepository<Customer, String> {}
