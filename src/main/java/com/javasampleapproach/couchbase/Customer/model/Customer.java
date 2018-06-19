package com.javasampleapproach.couchbase.Customer.model;

import com.couchbase.client.java.repository.annotation.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Document
public class Customer {

	@Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;

	@Field
	private String firstName;

	@Field
	private String lastName;
	
	public Customer(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Customer() {}
	
	public String getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	@Override
	public String toString() {
		return  " Nome: " + this.getFirstName() + " Cognome: " + this.getLastName();
	}
}
