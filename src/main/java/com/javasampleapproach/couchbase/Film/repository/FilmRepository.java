package com.javasampleapproach.couchbase.Film.repository;

import com.javasampleapproach.couchbase.Film.model.Film;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "film")
public interface FilmRepository extends CouchbaseRepository<Film, String> {

    @Query("SELECT META(FilmStore).id AS _ID, META(FilmStore).cas AS _CAS, FilmStore.* FROM FilmStore WHERE _class = 'com.javasampleapproach.couchbase.Film.model.Film'")
    List<Film> getFilmByFormatoQuery();
}