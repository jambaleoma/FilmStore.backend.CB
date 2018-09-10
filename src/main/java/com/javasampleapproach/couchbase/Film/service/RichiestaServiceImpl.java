package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Film.controller.RichiestaController;
import com.javasampleapproach.couchbase.Film.model.Richiesta;
import com.javasampleapproach.couchbase.Film.repository.RichiestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component("RichiestaService")
public class RichiestaServiceImpl implements RichiestaService {

    private static final Logger LOGGER = Logger.getLogger( RichiestaServiceImpl.class.getName() );

    @Autowired
    private RichiestaRepository richiestaRepository;

    @Override
    public List<Richiesta> getAllRichieste() {
        List<Richiesta> richieste = (List<Richiesta>) richiestaRepository.findAll();
        if (richieste == null) {
            throw new NotFoundException("Nessuna Richiesta Trovata");
        }
        StringBuilder listCustomer = new StringBuilder();
        listCustomer.append("\nLista Richieste:\n");
        for (Richiesta r : richieste) {
            listCustomer.append("Titolo Film Richiesto: " + r.getTitoloFilmRichiesto() + " Nome del Richiedente: " + r.getNomeCliente() + "\n");
        }
        LOGGER.info(listCustomer.toString());
        return richieste;
    }

    @Override
    public List<Richiesta> getAllRichiesteByNomeCliente(String nomeCliente) {
        List<Richiesta> richiesteByIdCliente = richiestaRepository.getRichiesteByNomeQuery(nomeCliente);
        if (richiesteByIdCliente.size() == 0) {
            throw new NotFoundException("Nessuna Richiesta Trovata");
        }
        return richiesteByIdCliente;
    }

    @Override
    public Richiesta getRichiestaById(String id) {
        Richiesta r = richiestaRepository.findOne(id);
        if (r == null)
            throw new NotFoundException("Richiesta con id: " + id + " NON Trovata");
        return r;
    }

    @Override
    public Richiesta addRichiesta(Richiesta r) {
        return richiestaRepository.save(r);
    }

    @Override
    public Richiesta updateRichiesta(Richiesta nuovaRichiesta, String id) {
        if (this.richiestaRepository.exists(id)) {
            Richiesta r = this.getRichiestaById(id);
            r.setTitoloFilmRichiesto(nuovaRichiesta.getTitoloFilmRichiesto());
            r.setFormatoFilmRichiesto(nuovaRichiesta.getFormatoFilmRichiesto());
            r.setDataInserimento(nuovaRichiesta.getDataInserimento());
            r.setNomeCliente(nuovaRichiesta.getNomeCliente());
            this.richiestaRepository.getCouchbaseOperations().update(r);
            return r;
        }
        else {
            throw new NotFoundException("Richiesta NON Aggiornata");
        }
    }

    @Override
    public Richiesta deleteRichiestaById(String id) {
        Richiesta r = this.getRichiestaById(id);
        richiestaRepository.delete(r);
        return r;
    }
}
