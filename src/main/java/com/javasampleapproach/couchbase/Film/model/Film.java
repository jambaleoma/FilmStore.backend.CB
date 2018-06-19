package com.javasampleapproach.couchbase.Film.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.Objects;

@Document
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @JsonProperty("_id")
    private String id;

    @Field
    private String nome;

    @Field
    private String formato;

    public Film(String nome, String formato) {
        this.nome = nome;
        this.formato = formato;
    }

    public Film() {}

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getFormato() {
        return formato;
    }

    @Override
    public String toString() {
        return " Nome: " + this.getNome() + " Formato: " + this.getFormato();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id) &&
                Objects.equals(nome, film.nome) &&
                Objects.equals(formato, film.formato);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nome, formato);
    }
}
