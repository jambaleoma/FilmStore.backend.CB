package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Customer.repository.CustomerRepository;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Film.model.Richiesta;
import com.javasampleapproach.couchbase.Film.repository.RichiestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("RichiestaService")
public class RichiestaServiceImpl implements RichiestaService {

    @Autowired
    private RichiestaRepository richiestaRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Richiesta> getAllRichieste() {
        List<Richiesta> richieste = (List<Richiesta>) richiestaRepository.findAll();
        if (richieste.size() == 0) {
            throw new NotFoundException("Nessuna Richiesta Trovata");
        }
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

        Richiesta r = this.addRichiesta(nuovaRichiesta);
        if (r != null) {
            return this.deleteRichiestaById(id);
        }
        else {
            throw new NotFoundException("Nessuna Richiesta Aggiornata");
        }
    }

    @Override
    public Richiesta deleteRichiestaById(String id) {
        Richiesta r = this.getRichiestaById(id);
        richiestaRepository.delete(r);
        return r;
    }
}
