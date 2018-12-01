package com.javasampleapproach.couchbase.Voto.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Data
@Document
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @JsonProperty("voto_id")
    private String id;

    @Field
    private String idFilm;

    @Field
    private String nomeFilm;

    @Field
    private String idCustomer;

    @Field
    private String firstNameCustomer;

    @Field
    private String lastNameCustomer;

    @Field
    private String dataCreazioneVoto;

    @Field
    private Integer votazione;

    @Field
    private Boolean like;

}
