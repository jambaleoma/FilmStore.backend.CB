package com.javasampleapproach.couchbase.Film.repository;

import com.javasampleapproach.couchbase.Film.model.Richiesta;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

import java.util.List;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "richiesta")
public interface RichiestaRepository extends CouchbasePagingAndSortingRepository<Richiesta, String> {

    @Query("#{#n1ql.selectEntity} where #{#n1ql.filter} AND idCliente like $1")
    List<Richiesta> getRichiestaByFormatoQuery(String idCliente);
}
