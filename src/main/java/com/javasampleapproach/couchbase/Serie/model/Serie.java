package com.javasampleapproach.couchbase.Serie.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javasampleapproach.couchbase.Stagione.model.Stagione;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.List;

@Document
@Data
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @JsonProperty("serie_id")
    private String _id;

    @Field
    private String nome;

    @Field
    private String locandina;

    @Field
    private List<Stagione> stagioni;

    @Field
    private Long dataCreazione;
}
