package com.javasampleapproach.couchbase.Serie.service;

import com.javasampleapproach.couchbase.Serie.model.Stagione;

import java.util.List;

public interface StagioneService {

    List<Stagione> getAllStagioni();
    List<Stagione> getAllStagioniByIdSerie(String name);
    Stagione getStagioneById(String id);
    Stagione addStagione(Stagione s);
    Stagione updateStagione (Stagione nuovaStagione, String id);
    Stagione deleteStagioneById(String id);
}
