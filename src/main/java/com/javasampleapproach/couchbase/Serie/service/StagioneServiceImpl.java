package com.javasampleapproach.couchbase.Serie.service;

import com.javasampleapproach.couchbase.Exception.AlreadyExistException;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Serie.model.Stagione;
import com.javasampleapproach.couchbase.Serie.repository.StagioneRepository;
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
        if (stagioni.size() == 0) {
            throw new NotFoundException("Nessuna Stagione con Id_Serie: " + serie_id + " è stata Trovata");
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
            Stagione StagioneDaAggiornare = stagioneRepository.findOne(id);
            StagioneDaAggiornare.setNumeroStagione(nuovaStagione.getNumeroStagione());
            StagioneDaAggiornare.setFormato(nuovaStagione.getFormato());
            StagioneDaAggiornare.setAnno(nuovaStagione.getAnno());
            StagioneDaAggiornare.setLinguaAudio(nuovaStagione.getLinguaAudio());
            StagioneDaAggiornare.setLinguaSottotitoli(nuovaStagione.getLinguaSottotitoli());
            StagioneDaAggiornare.setNumeroEpisodi(nuovaStagione.getNumeroEpisodi());
            StagioneDaAggiornare.setEpisodi(nuovaStagione.getEpisodi());
            StagioneDaAggiornare.setUrlLocandina(nuovaStagione.getUrlLocandina());
            StagioneDaAggiornare.setTrama(nuovaStagione.getTrama());
            this.stagioneRepository.getCouchbaseOperations().update(StagioneDaAggiornare);
            return StagioneDaAggiornare;
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
}
