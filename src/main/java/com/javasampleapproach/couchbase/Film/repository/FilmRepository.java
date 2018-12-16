package com.javasampleapproach.couchbase.Film.repository;

import com.javasampleapproach.couchbase.Film.model.Film;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

import java.util.List;
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "film")
public interface FilmRepository extends CouchbasePagingAndSortingRepository<Film, String> {

    @Query("SELECT COUNT(*) AS count FROM #{#n1ql.bucket} WHERE #{#n1ql.filter} and formato = $1")
    List<Film> getFilmByFormatoQuery(String formato);
}