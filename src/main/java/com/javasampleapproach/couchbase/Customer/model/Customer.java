package com.javasampleapproach.couchbase.Customer.model;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.List;

@Data
@Document
public class Customer {

	@Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;

	@Field
	private String firstName;

	@Field
	private String lastName;

	@Field
	private String sesso;

	@Field
	private String dataDiNascita;

	@Field
	private String password;

	@Field
	private String label;

	@Field
	private String value;

	@Field
	private Integer numeroRichieste;

	@Field
	private List<String> categoriePreferite;

	@Field
	private boolean isAdmin;

	@Field
	private boolean avatar;

}
