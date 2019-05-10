package com.javasampleapproach.couchbase.Film.service;

import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.SimpleN1qlQuery;
import com.javasampleapproach.couchbase.Customer.service.CustomerServiceImpl;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Film.model.Film;
import com.javasampleapproach.couchbase.Film.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Component("FilmService")
public class FilmServiceImpl implements FilmService {

    private static final Logger LOGGER = Logger.getLogger( CustomerServiceImpl.class.getName() );

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public List<Film> getAllFilms() {
        List<Film> films = (List<Film>) filmRepository.findAll();
        if (films == null) {
            throw new NotFoundException("Nessun Film Trovato");
        }
        return films;
    }

    @Override
    public List<Film> getAllNewFilms(Integer numeroNuoviFilm) {
        List<Film> allNewFilms = this.getAllFilms();
        Collections.sort(allNewFilms, new DataCreazioneComparatore());
        allNewFilms = allNewFilms.subList(0, numeroNuoviFilm);
        return allNewFilms;
    }

    @Override
    public List<Film> getAllFilmsByName(String nome) {
        List<Film> films = new ArrayList<>();
        for (Film f : filmRepository.findAll()) {
            if (f.getNome().matches("(.*)" + nome + "(.*)"))
                films.add(f);
        }
        if (films.size() == 0) {
            throw new NotFoundException("Nessun Film con Nome: " + nome + " è stato Trovato");
        }
        return films;
    }

    @Override
    public List<Film> getAllFilmsByCategory(String categoria) {
        List<Film> films = new ArrayList<>();
        for (Film f : filmRepository.findAll()) {
            for(int i = 0; i < f.getCategoria().size(); i++) {
                if (i > 3)
                    break;
                if (f.getCategoria().get(i).equals(categoria))
                    films.add(f);
            }
        }
        if (films.size() == 0) {
            throw new NotFoundException("Nessun Film con categoria: " + categoria + " è stato Trovato");
        }
        StringBuilder listFilms = new StringBuilder();
        listFilms.append("\nLista Film Per Categoria:\n");
        listFilms.append("\nCategoria: " + categoria + "\n");
        LOGGER.info(listFilms.toString());
        return films;
    }

    @Override
    public Film getFilmById(String id) {
        Film f =  filmRepository.findOne(id);
        if (f == null)
            throw new NotFoundException("Film con id: " + id + " NON Trovato");
        return f;
    }

    @Override
    public List<Film> getFilmByFormatoQuery(String formato) {
        List<Film> filmsByFormatoN1ql;
        try {
            String statement = "SELECT *, META().id AS _ID, META().cas AS _CAS " +
                    "FROM FilmStore " +
                    "WHERE _class = 'com.javasampleapproach.couchbase.Film.model.Film' " +
                    "AND formato = '" + formato + "'";
            SimpleN1qlQuery query = N1qlQuery.simple(statement);
            filmsByFormatoN1ql = filmRepository.getCouchbaseOperations().findByN1QL(query, Film.class);
            System.out.println(filmsByFormatoN1ql);
        } catch (Exception ex) {
            throw ex;
        }
        return filmsByFormatoN1ql;
    }

    @Override
    public Film addFilm(Film f) {
        return filmRepository.save(f);
    }

    @Override
    public Film updateFilmById(Film nuovoFilm, String id) {
        if (filmRepository.exists(id)) {
            Film filmDaAggiornare = filmRepository.findOne(id);
            filmDaAggiornare.setNome(nuovoFilm.getNome());
            filmDaAggiornare.setAnno(nuovoFilm.getAnno());
            filmDaAggiornare.setFormato(nuovoFilm.getFormato());
            filmDaAggiornare.setCategoria(nuovoFilm.getCategoria());
            filmDaAggiornare.setLinguaAudio(nuovoFilm.getLinguaAudio());
            filmDaAggiornare.setLinguaSottotitoli(nuovoFilm.getLinguaSottotitoli());
            filmDaAggiornare.setLocandina(nuovoFilm.getLocandina());
            filmDaAggiornare.setTrama(nuovoFilm.getTrama());
            filmDaAggiornare.setDataCreazione(nuovoFilm.getDataCreazione());
            this.filmRepository.getCouchbaseOperations().update(filmDaAggiornare);
            return filmDaAggiornare;
        } else {
            throw new NotFoundException("Film con id: " + id + " NON Trovato");
        }
    }

    @Override
    public Film deleteFilmById(String id) {
        Film f = this.getFilmById(id);
        filmRepository.delete(f);
        return f;
    }

/*
    //Riempie il DB Couchbase Con i film nel disco di BackUp
    @Override
    public List<Film> addListaFilms(String formato) {
        //PC
        File from1080 = new File("H:\\Film\\1080p");
        File from720 = new File("H:\\Film\\720p");
        File fromDVDRip = new File("H:\\Film\\DVDRip");
        //MAC
        //File from1080 = new File("/Volumes/TOSHIBA EXT/Film/1080p");
        //File from720 = new File("/Volumes/TOSHIBA EXT/Film/720p");
        //File fromDVDRip = new File("/Volumes/TOSHIBA EXT/Film/DVDRip");
        File[] f1080 = trovaFile(from1080);
        File[] f720 = trovaFile(from720);
        File[] fDVD = trovaFile(fromDVDRip);
        List<Film> listaFilm = new ArrayList<>();
        if (formato.equals("FULL-HD")) {
            for (int i=0; i<f1080.length; i++) {
                listaFilm.add(this.addFilm(new Film(f1080[i].getName(), formato)));
            }
        }
        if (formato.equals("HD")) {
            for (int i=0; i<f720.length; i++) {
                listaFilm.add(this.addFilm(new Film(f720[i].getName(), formato)));
            }
        }
        if (formato.equals("DVD")) {
            for (int i=0; i<fDVD.length; i++) {
                listaFilm.add(this.addFilm(new Film(fDVD[i].getName(), formato)));
            }
        }
        return listaFilm;
    }

    private static File[] trovaFile(File from) {
        File[] files = from.listFiles();
        return files;
    }
*/

}