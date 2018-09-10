package com.javasampleapproach.couchbase.Serie.service;

import com.javasampleapproach.couchbase.Serie.model.Serie;

import java.util.List;

public interface SerieService {

    List<Serie> getAllSerie();
    List<Serie> getAllSerieByName(String name);
    Serie getSerieById(String id);
    Serie addSerie(Serie s);
    Serie updateSerie (Serie nuovaSerie, String id);
    Serie deleteSerieById(String id);
    List<Serie> addListaSerieByFormato(String formato);
}
