package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Film.model.Film;
import com.javasampleapproach.couchbase.Film.model.Richiesta;
import com.javasampleapproach.couchbase.Film.repository.RichiestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("RichiestaService")
public class RichiestaServiceImpl implements RichiestaService {

    @Autowired
    private RichiestaRepository richiestaRepository;

    @Override
    public List<Richiesta> getAllRichieste() {
        List<Richiesta> richieste = (List<Richiesta>) richiestaRepository.findAll();
        if (richieste.size() == 0) {
            throw new NotFoundException("Nessuna Richiesta Trovata");
        }
        return richieste;
    }

    @Override
    public List<Richiesta> getAllRichiesteByIdCliente(String idCliente) {
        return null;
    }

    @Override
    public Richiesta getRichiesteById(String id) {
        return null;
    }

    @Override
    public Richiesta addRichiesta(Richiesta r) {
        return richiestaRepository.save(r);
    }

    @Override
    public Richiesta updateRichiesta(Richiesta nuovaRichiesta, String id) {
        return null;
    }

    @Override
    public Richiesta deleteRichiestaById(String id) {
        return null;
    }

    @Override
    public List<Richiesta> getRichiestaByIdClienteQuery(String idCliente) {
        return null;
    }
}
