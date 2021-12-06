package com.javasampleapproach.couchbase.Stagione.service;

import com.javasampleapproach.couchbase.Exception.AlreadyExistException;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Stagione.model.Stagione;
import com.javasampleapproach.couchbase.Stagione.repository.StagioneRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
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

    // Riempie il DB Couchbase Con le Stagioni nel JSON di riferimento
   /* @Override
    public void addListaStagioni() {

        JSONParser parser = new JSONParser();

        JSONArray a = null;
        try {
            a = (JSONArray) parser.parse(new FileReader("C:\\Users\\Enzo\\Desktop\\responseStagioni.json"));

            for (Object o : a) {
                JSONObject stagione = (JSONObject) o;

                String serie_id = (String) stagione.get("serie_id");
                String nome_serie = (String) stagione.get("nome_serie");
                Integer numeroStagione = (Integer) stagione.get("numeroStagione");
                String formato = (String) stagione.get("formato");
                Integer anno = (Integer) stagione.get("anno");
                Integer numeroEpisodi = (Integer) stagione.get("numeroEpisodi");
                JSONArray episodi = (JSONArray) stagione.get("episodi");
                String[] episodiList = new String[episodi.size()];
                if (episodi != null) {
                    for (int i = 0; i < episodi.size(); i++) {
                        episodiList[i] = episodi.get(i).toString();
                    }
                }
                JSONArray linguaAudio = (JSONArray) stagione.get("linguaAudio");
                List<String> lingueAudio = new ArrayList<>();
                if (linguaAudio != null) {
                    for (Object la : linguaAudio) {
                        lingueAudio.add(la.toString());
                    }
                }
                JSONArray linguaSottotitoli = (JSONArray) stagione.get("linguaSottotitoli");
                List<String> lingueSottotitoli = new ArrayList<>();
                if (linguaSottotitoli != null) {
                    for (Object ls : linguaSottotitoli) {
                        lingueSottotitoli.add(ls.toString());
                    }
                }
                String trama = (String) stagione.get("trama");
                String locandina = (String) stagione.get("locandina");

                Stagione newStagione = new Stagione();
                newStagione.setSerie_id(serie_id);
                newStagione.setNome_serie(nome_serie);
                newStagione.setNumeroStagione(numeroStagione);
                newStagione.setFormato(formato);
                newStagione.setAnno(anno);
                newStagione.setNumeroEpisodi(numeroEpisodi);
                newStagione.setEpisodi(episodiList);
                newStagione.setLinguaAudio(lingueAudio);
                newStagione.setLinguaSottotitoli(lingueSottotitoli);
                newStagione.setTrama(trama);
                newStagione.setLocandina(locandina);

                this.addStagione(newStagione);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
