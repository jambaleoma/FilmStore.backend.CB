package com.javasampleapproach.couchbase.Serie.repository;

import com.javasampleapproach.couchbase.Serie.model.Serie;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "serie")
public interface SerieRepository extends CouchbasePagingAndSortingRepository<Serie, String> {}