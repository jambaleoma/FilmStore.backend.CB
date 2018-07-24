package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Film.model.Film;
import com.javasampleapproach.couchbase.Film.model.Richiesta;

import java.util.List;

public interface RichiestaService {
    List<Richiesta> getAllRichieste();
    List<Richiesta> getAllRichiesteByIdCliente(String idCliente);
    Richiesta getRichiesteById(String id);
    Richiesta addRichiesta(Richiesta r);
    Richiesta updateRichiesta (Richiesta nuovaRichiesta, String id);
    Richiesta deleteRichiestaById(String id);
    List<Richiesta> getRichiestaByIdClienteQuery(String idCliente);
}
