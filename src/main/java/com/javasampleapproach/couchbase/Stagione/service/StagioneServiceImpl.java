package com.javasampleapproach.couchbase.Stagione.service;

import com.javasampleapproach.couchbase.Exception.AlreadyExistException;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Stagione.model.Stagione;
import com.javasampleapproach.couchbase.Stagione.repository.StagioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("StagioneService")
public class StagioneServiceImpl implements StagioneService {

    @Autowired
    private StagioneRepository stagioneRepository;

    @Override
    public List<Stagione> getAllStagioni() {
        List<Stagione> stagioni = (List<Stagione>) stagioneRepository.findAll();
        if (stagioni.size() == 0) {
            throw new NotFoundException("Nessuna Stagione Trovata");
        }
        return stagioni;
    }

    @Override
    public List<Stagione> getAllStagioniByIdSerie(String serie_id) {
        List<Stagione> stagioni = new ArrayList<>();
        for (Stagione s : stagioneRepository.findAll()) {
            if (s.getSerie_id().equals(serie_id)) {
                stagioni.add(s);
            }
        }
        return stagioni;
    }

    @Override
    public Stagione getStagioneById(String id) {
        Stagione s =  stagioneRepository.findOne(id);
        if (s == null)
            throw new NotFoundException("Stagione con id: " + id + " NON Trovata");
        return s;
    }

    @Override
    public Stagione addStagione(Stagione s) {
        for (Stagione stagione : stagioneRepository.findAll()) {
            if (stagione.getId().equals(s.getId()))
                throw new AlreadyExistException("La Stagione con Id: " + s.getId() + " è già presente, Cambia l'ID se vuoi inserire la Stagione");
        }
        return stagioneRepository.save(s);
    }

    @Override
    public Stagione updateStagione(Stagione nuovaStagione, String id) {
        if (stagioneRepository.exists(id)) {
            Stagione stagioneDaAggiornare = stagioneRepository.findOne(id);
            stagioneDaAggiornare.setNumeroStagione(nuovaStagione.getNumeroStagione());
            stagioneDaAggiornare.setFormato(nuovaStagione.getFormato());
            stagioneDaAggiornare.setAnno(nuovaStagione.getAnno());
            stagioneDaAggiornare.setLinguaAudio(nuovaStagione.getLinguaAudio());
            stagioneDaAggiornare.setLinguaSottotitoli(nuovaStagione.getLinguaSottotitoli());
            stagioneDaAggiornare.setNumeroEpisodi(nuovaStagione.getNumeroEpisodi());
            stagioneDaAggiornare.setEpisodi(nuovaStagione.getEpisodi());
            stagioneDaAggiornare.setLocandina(nuovaStagione.getLocandina());
            stagioneDaAggiornare.setTrama(nuovaStagione.getTrama());
            stagioneRepository.getCouchbaseOperations().update(stagioneDaAggiornare);
            return stagioneDaAggiornare;
        } else {
            throw new NotFoundException("Stagione con id: " + id + " NON Trovata");
        }
    }

    @Override
    public Stagione deleteStagioneById(String id) {
        if (stagioneRepository.exists(id)) {
            Stagione g = this.getStagioneById(id);
            stagioneRepository.delete(g);
            return g;
        } else {
            throw new NotFoundException("Stagione con id: " + id + " NON Trovata");
        }
    }

    @Override
    public void deleteStagioniBySerieId(String id) {
        List<Stagione> stagioni = getAllStagioniByIdSerie(id);
        for (Stagione s : stagioni) {
            stagioneRepository.delete(s);
        }
    }
}
