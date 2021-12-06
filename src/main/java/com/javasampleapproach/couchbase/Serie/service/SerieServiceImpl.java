package com.javasampleapproach.couchbase.Serie.service;

import com.javasampleapproach.couchbase.Exception.AlreadyExistException;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Serie.model.Serie;
import com.javasampleapproach.couchbase.Serie.repository.SerieRepository;
import com.javasampleapproach.couchbase.Stagione.model.Stagione;
import com.javasampleapproach.couchbase.Stagione.service.StagioneService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Component("SerieService")
public class SerieServiceImpl implements SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private StagioneService stagioneService;

    @Override
    public List<Serie> getAllSerie() {
        List<Serie> serie = (List<Serie>) serieRepository.findAll();
        if (serie.size() == 0) {
            throw new NotFoundException("Nessuna Serie Trovata");
        }
        return serie;
    }

    @Override
    public List<Serie> getAllNewSerie(Integer numeroNuoveSerie) {
        List<Serie> allNewSerie = this.getAllSerie();
        Collections.sort(allNewSerie, new DataCreazioneComparatore());
        allNewSerie = allNewSerie.subList(0, numeroNuoveSerie);
        return allNewSerie;
    }

    @Override
    public List<Serie> getAllSerieByName(String nome) {
        List<Serie> serie = new ArrayList<>();
        for (Serie s : serieRepository.findAll()) {
            if (s.getNome().equals(nome)) {
                serie.add(s);
            }
        }
        if (serie.size() == 0) {
            throw new NotFoundException("Nessuna Serie con Nome: " + nome + " è stata Trovata");
        }
        return serie;
    }

    @Override
    public Serie getSerieById(String id) {
        Serie s =  serieRepository.findOne(id);
        if (s == null)
            throw new NotFoundException("Serie con id: " + id + " NON Trovata");
        return s;
    }

    @Override
    public Serie addSerie(Serie s) {
        for (Serie serie : serieRepository.findAll()) {
            if (serie.get_id().equals(s.get_id()))
                throw new AlreadyExistException("La Serie con Id: " + s.get_id() + " è già presente, Cambia l'ID se vuoi inserire la Serie");
        }
        String newDateSerie = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        s.setDataCreazione(Long.parseLong(newDateSerie));
        return serieRepository.save(s);
    }

    @Override
    public Serie updateSerie(Serie nuovaSerie, String id) {
        if (serieRepository.exists(id)) {
            Serie serieDaAggiornare = serieRepository.findOne(id);
            List<Stagione> stagioni = stagioneService.getAllStagioniByIdSerie(id);
            serieDaAggiornare.setNome(nuovaSerie.getNome());
            serieDaAggiornare.setLocandina(nuovaSerie.getLocandina());
            serieDaAggiornare.setStagioni(stagioni);
            serieDaAggiornare.setDataCreazione(nuovaSerie.getDataCreazione());
            this.serieRepository.getCouchbaseOperations().update(serieDaAggiornare);
            return serieDaAggiornare;
        } else {
            throw new NotFoundException("Serie con id: " + id + " NON Trovata");
        }
    }

    @Override
    public Serie deleteSerieById(String id) {
        if (serieRepository.exists(id)) {
            Serie g = this.getSerieById(id);
            serieRepository.delete(g);
            return g;
        } else {
            throw new NotFoundException("Serie con id: " + id + " NON Trovata");
        }
    }

    // Riempie il DB Couchbase Con le SerieTV nel JSON di riferimento
    // Commentare le righe 79 e 80 in addSerie per evitare di sovrascrivere la data creazione
    /*@Override
    public void addListaSerie() {
        JSONParser parser = new JSONParser();

        JSONArray a = null;
        try {
            a = (JSONArray) parser.parse(new FileReader("C:\\Users\\Enzo\\Desktop\\responseSerie.json"));

            for (Object o : a) {
                JSONObject serie = (JSONObject) o;

                String serie_id = (String) serie.get("serie_id");
                String nome = (String) serie.get("nome");
                String locandina = (String) serie.get("locandina");
                JSONArray stagioni = (JSONArray) serie.get("stagioni");
                List<Stagione> stagioniList = new ArrayList<>();
                if (stagioni != null) {
                    for (Object s : stagioni) {
                        JSONObject stagione = (JSONObject) s;

                        String serie_idStagione = (String) stagione.get("serie_id");
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
                        String locandinaStagione = (String) stagione.get("locandina");

                        Stagione newStagione = new Stagione();
                        newStagione.setSerie_id(serie_idStagione);
                        newStagione.setNome_serie(nome_serie);
                        newStagione.setNumeroStagione(numeroStagione);
                        newStagione.setFormato(formato);
                        newStagione.setAnno(anno);
                        newStagione.setNumeroEpisodi(numeroEpisodi);
                        newStagione.setEpisodi(episodiList);
                        newStagione.setLinguaAudio(lingueAudio);
                        newStagione.setLinguaSottotitoli(lingueSottotitoli);
                        newStagione.setTrama(trama);
                        newStagione.setLocandina(locandinaStagione);

                        stagioniList.add(newStagione);
                    }
                }
                Long dataCreazione = (Long) serie.get("dataCreazione");

                Serie newSerie = new Serie();
                newSerie.set_id(serie_id);
                newSerie.setNome(nome);
                newSerie.setLocandina(locandina);
                newSerie.setStagioni(stagioniList);
                newSerie.setDataCreazione(dataCreazione);

                this.addSerie(newSerie);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

}
