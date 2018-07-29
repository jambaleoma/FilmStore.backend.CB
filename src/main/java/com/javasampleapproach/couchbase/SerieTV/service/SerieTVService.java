package com.javasampleapproach.couchbase.SerieTV.service;

import com.javasampleapproach.couchbase.SerieTV.model.SerieTV;
import java.util.List;

public interface SerieTVService {

    List<SerieTV> getAllSerieTVs();
    List<SerieTV> getAllSerieTVByName(String nome);
    SerieTV getSerieTVById(String id);
    SerieTV addSerieTV(SerieTV stv);
    SerieTV deleteSerieTVById(String id);
    List<SerieTV> addListaSerieTVs(String formato);
    List<SerieTV> getSerieTVsByFormatoQuery(String formato);
}
