package com.javasampleapproach.couchbase.Serie.service;

import com.javasampleapproach.couchbase.Exception.AlreadyExistException;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Serie.model.Serie;
import com.javasampleapproach.couchbase.Serie.repository.SerieRepository;
import com.javasampleapproach.couchbase.Stagione.model.Stagione;
import com.javasampleapproach.couchbase.Stagione.service.StagioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
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

   /* //Riempie il DB Couchbase Con le SerieTV nel disco di BackUp
    @Override
    public List<Serie> addListaSerieByFormato(String formato) {
        //PC
        File from1080 = new File("H:\\SerieTV\\1080p");
        File fromDVDRip = new File("H:\\SerieTV\\WEBRip");
        //MAC
        //File from1080 = new File("/Volumes/TOSHIBA EXT/SerieTV/1080p");
        //File fromDVDRip = new File("/Volumes/TOSHIBA EXT/SerieTV/WEBRip");
        File[] f1080 = trovaFile(from1080);
        File[] fWEB = trovaFile(fromDVDRip);
        List<Serie> listaSerieTV = new ArrayList<>();
        if (formato.equals("FULL-HD")) {
            for (int i=0; i<f1080.length; i++) {
              // listaSerieTV.add(this.addSerie(new Serie(f1080[i].getName(), formato)));
            }
        }
        if (formato.equals("WEB")) {
            for (int i=0; i<fWEB.length; i++) {
               // listaSerieTV.add(this.addSerie(new Serie(fWEB[i].getName(), formato)));
            }
        }
        return listaSerieTV;
    }*/

    private static File[] trovaFile(File from) {
        File[] files = from.listFiles();
        return files;
    }

}
