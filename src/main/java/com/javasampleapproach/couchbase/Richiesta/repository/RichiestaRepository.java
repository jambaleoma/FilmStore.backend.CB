package com.javasampleapproach.couchbase.Richiesta.repository;

import com.javasampleapproach.couchbase.Richiesta.model.Richiesta;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "richiesta")
public interface RichiestaRepository extends CouchbasePagingAndSortingRepository<Richiesta, String> {
}
