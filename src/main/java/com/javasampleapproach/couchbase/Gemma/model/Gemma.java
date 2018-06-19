package com.javasampleapproach.couchbase.Gemma.model;

import com.couchbase.client.java.repository.annotation.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
public class Gemma {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String description;

    @Field
    @NotNull
    private Integer price;

    public Gemma(String id, String name, String description, Integer value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = value;
    }

    public Gemma() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return  "id: " + this.getId() + " Nome: " + this.getName() + " Descrizione: " + this.getDescription() + " Prezzo: " + this.getPrice();
    }
}
