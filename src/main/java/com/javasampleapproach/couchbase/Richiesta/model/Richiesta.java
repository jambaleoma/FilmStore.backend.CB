package com.javasampleapproach.couchbase.Richiesta.model;

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
    private String id;

    @Field
    private String titoloFilmRichiesto;

    @Field
    private String formatoFilmRichiesto;

    @Field
    private String dataInserimento;

    @Field
    private String nomeCliente;

    @Field
    private String stato;

    @Field
    private String note;
}
