package com.javasampleapproach.couchbase.Film.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.List;

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

    @Field
    private List<String> categoria;

    @Field
    private List<String> linguaAudio;

    @Field
    private List<String> linguaSottotitoli;

    @Field
    private String trama;

    @Field
    private String locandina;

    @Field
    private Long dataCreazione;
}
