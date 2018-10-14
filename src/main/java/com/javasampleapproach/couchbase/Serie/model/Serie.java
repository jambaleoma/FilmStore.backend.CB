package com.javasampleapproach.couchbase.Serie.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import javax.validation.constraints.NotNull;

@Document
@Data
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @JsonProperty("_id")
    private String id;

    @Field
    private String nome;

    @Field
    private String stagioni;

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
    private String linguaAudio;

    @Field
    private String linguaSottotitoli;

    @Field
    private String trama;

    @Field
    private String urlLocandina;
}
