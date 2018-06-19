package com.javasampleapproach.couchbase.Gemma.repository;

import com.javasampleapproach.couchbase.Gemma.model.Gemma;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "gemma")
public interface GemmaRepository extends CouchbasePagingAndSortingRepository<Gemma, String> {}