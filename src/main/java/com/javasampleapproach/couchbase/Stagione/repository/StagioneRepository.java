package com.javasampleapproach.couchbase.Stagione.repository;

import com.javasampleapproach.couchbase.Stagione.model.Stagione;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "stagione")
public interface StagioneRepository extends CouchbasePagingAndSortingRepository<Stagione, String> {}
