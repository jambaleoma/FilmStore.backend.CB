package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Film.model.Film;
import java.util.List;

public interface FilmService {

        List<Film> getAllFilms();
        List<Film> getAllFilmsByName(String nome);
        Film getFilmById(String id);
        Film addFilm(Film f);
        Film deleteFilmById(String id);
        //List<Film> addListaFilms(String formato);
        List<Film> getFilmByFormatoQuery(String formato);
}
