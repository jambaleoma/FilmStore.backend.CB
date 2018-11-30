package com.javasampleapproach.couchbase.Voto.repository;

import com.javasampleapproach.couchbase.Voto.model.Voto;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "voto")
public interface VotoRepository extends CouchbasePagingAndSortingRepository<Voto, String> {}
