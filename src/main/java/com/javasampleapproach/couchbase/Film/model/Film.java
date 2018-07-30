package com.javasampleapproach.couchbase.Film.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.Objects;

@Document
@Data
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @JsonProperty("_id")
    private String id;

    @Field
    private String nome;

    @Field
    private Integer anno;

    @Field
    private String formato;

    public Film(String nome, String formato) {
        this.nome = nome;
        this.formato = formato;
    }
}
