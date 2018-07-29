package com.javasampleapproach.couchbase.SerieTV.repository;

import com.javasampleapproach.couchbase.SerieTV.model.SerieTV;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

import java.util.List;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "serieTV")
public interface SerieTVRepository extends CouchbasePagingAndSortingRepository<SerieTV, String> {
    @Query("#{#n1ql.selectEntity} where _class like '%.SerieTV' AND formato like $1")
    List<SerieTV> getSerieTVsByFormatoQuery(String formato);
}
