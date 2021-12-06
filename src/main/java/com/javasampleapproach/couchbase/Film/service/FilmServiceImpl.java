package com.javasampleapproach.couchbase.Film.service;

import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.SimpleN1qlQuery;
import com.javasampleapproach.couchbase.Customer.service.CustomerServiceImpl;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Film.model.Film;
import com.javasampleapproach.couchbase.Film.repository.FilmRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
    public String getRecentOlderYear() {
        String recentOlderYear = "";
        int recentYear = 0;
        int olderYear = Calendar.getInstance().get(Calendar.YEAR);
        for (Film f : filmRepository.findAll()) {
            if (f.getAnno() < olderYear) {
                olderYear = f.getAnno();
            }
            if (f.getAnno() > recentYear) {
                recentYear = f.getAnno();
            }
            recentOlderYear = olderYear + "-" + recentYear;
        }
        return recentOlderYear;
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
        String newDateFilm = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        f.setDataCreazione(Long.parseLong(newDateFilm));
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

    // Riempie il DB Couchbase Con i film nel JSON di riferimento
    // Commentare le righe 125 e 126 in addFilm per evitare di sovrascrivere la data creazione
    /*@Override
    public void addListaFilms() {

        JSONParser parser = new JSONParser();

        JSONArray a = null;
        try {
            a = (JSONArray) parser.parse(new FileReader("C:\\Users\\Enzo\\Desktop\\responseFilm.json"));

            for (Object o : a) {
                JSONObject film = (JSONObject) o;

                String nome = (String) film.get("nome");
                Integer anno = (Integer) film.get("anno");
                String formato = (String) film.get("formato");
                JSONArray categoria = (JSONArray) film.get("categoria");
                List<String> categoriaList = new ArrayList<>();
                if (categoria != null) {
                    for (Object la : categoria) {
                        categoriaList.add(la.toString());
                    }
                }
                JSONArray linguaAudio = (JSONArray) film.get("linguaAudio");
                List<String> lingueAudio = new ArrayList<>();
                if (linguaAudio != null) {
                    for (Object la : linguaAudio) {
                        lingueAudio.add(la.toString());
                    }
                }
                JSONArray linguaSottotitoli = (JSONArray) film.get("linguaSottotitoli");
                List<String> lingueSottotitoli = new ArrayList<>();
                if (linguaSottotitoli != null) {
                    for (Object ls : linguaSottotitoli) {
                        lingueSottotitoli.add(ls.toString());
                    }
                }
                String trama = (String) film.get("trama");
                String locandina = (String) film.get("locandina");
                Long dataCreazione = (Long) film.get("dataCreazione");

                Film newFilm = new Film();
                newFilm.setNome(nome);
                newFilm.setAnno(anno);
                newFilm.setFormato(formato);
                newFilm.setCategoria(categoriaList);
                newFilm.setLinguaAudio(lingueAudio);
                newFilm.setLinguaSottotitoli(lingueSottotitoli);
                newFilm.setTrama(trama);
                newFilm.setLocandina(locandina);
                newFilm.setDataCreazione(dataCreazione);

                this.addFilm(newFilm);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

}