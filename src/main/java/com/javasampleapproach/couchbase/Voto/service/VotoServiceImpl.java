package com.javasampleapproach.couchbase.Voto.service;

import com.javasampleapproach.couchbase.Exception.AlreadyExistException;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Voto.model.Voto;
import com.javasampleapproach.couchbase.Voto.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("VotoService")
public class VotoServiceImpl implements VotoService{

    @Autowired
    private VotoRepository votoRepository;

    @Override
    public List<Voto> getAllVoti() {
        List<Voto> voti = (List<Voto>) votoRepository.findAll();
        if (voti == null) {
            throw new NotFoundException("Nessun Voto Trovato");
        }
        return voti;
    }

    @Override
    public Voto getVotoByFilmId_Customer(String idFilm, String idCustomer) {
        Voto votoFilm = new Voto();
        for (Voto v : votoRepository.findAll()) {
            if ((v.getIdFilm().equals(idFilm)) && (v.getIdCustomer().equals(idCustomer))) {
                votoFilm = v;
            }
        }
        return votoFilm;
    }

    @Override
    public Voto getVotoById(String id) {
        Voto v =  votoRepository.findOne(id);
        if (v == null)
            throw new NotFoundException("Voto con id: " + id + " NON Trovato");
        return v;
    }

    @Override
    public Voto addVoto(Voto v) {
        for (Voto voto : votoRepository.findAll()) {
            if (voto.getId().equals(v.getId()))
                throw new AlreadyExistException("Il Voto con Id: " + v.getId() + " è già presente, Cambia l'ID se vuoi inserire il Voto");
        }
        return votoRepository.save(v);
    }

    @Override
    public Voto updateVoto(Voto nuovoVoto, String id) {
        if (votoRepository.exists(id)) {
            Voto votoDaAggiornare = votoRepository.findOne(id);
            votoDaAggiornare.setVotazione(nuovoVoto.getVotazione());
            votoDaAggiornare.setIdCustomer(nuovoVoto.getIdCustomer());
            votoDaAggiornare.setIdFilm(nuovoVoto.getIdFilm());
            votoDaAggiornare.setLike(nuovoVoto.getLike());
            votoRepository.getCouchbaseOperations().update(votoDaAggiornare);
            return votoDaAggiornare;
        } else {
            throw new NotFoundException("Voto con id: " + id + " NON Trovato");
        }
    }

    @Override
    public Voto deleteVotoById(String id) {
        if (votoRepository.exists(id)) {
            Voto v = this.getVotoById(id);
            votoRepository.delete(v);
            return v;
        } else {
            throw new NotFoundException("Voto con id: " + id + " NON Trovato");
        }
    }

    @Override
    public void deleteVotoByFilmId_CustomerId(String filmId, String customerId) {
        Voto v  = getVotoByFilmId_Customer(filmId, customerId);
        votoRepository.delete(v);
    }
}
