package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Film.model.Richiesta;

import java.util.List;

public interface RichiestaService {
    List<Richiesta> getAllRichieste();
    List<Richiesta> getAllRichiesteByNomeCliente(String nomeCliente);
    Richiesta getRichiestaById(String id);
    Richiesta addRichiesta(Richiesta r);
    Richiesta updateRichiesta (Richiesta nuovaRichiesta, String id);
    Richiesta deleteRichiestaById(String id);
}
