package com.javasampleapproach.couchbase.Stagione.model;

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
public class Stagione {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @JsonProperty("stagione_id")
    private String id;

    @Field
    private String serie_id;

    @Field
    private String nome_serie;

    @Field
    private Integer numeroStagione;

    @Field
    private String formato;

    @Field
    private Integer anno;

    @Field
    private Integer numeroEpisodi;

    @Field
    private String[] episodi;

    @Field
    private List<String> linguaAudio;

    @Field
    private List<String> linguaSottotitoli;

    @Field
    private String trama;

    @Field
    private String locandina;
}
