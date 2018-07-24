package com.javasampleapproach.couchbase.Film.model;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;
import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Document
@Data
public class Richiesta {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @JsonProperty("_id")
    private String id;

    @Field
    private Film filmRichiesto;

    @Field
    private String dataInserimento;

    @Field
    private String idCliente;
}da
