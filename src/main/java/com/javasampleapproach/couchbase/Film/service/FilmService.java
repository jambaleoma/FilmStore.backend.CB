package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Film.model.Film;

import java.util.List;

public interface FilmService {

        List<Film> getAllFilms();
        List<Film> getAllNewFilms (Integer numeroNuoviFilm);
        List<Film> getAllFilmsByName(String nome);
        List<Film> getAllFilmsByCategory(String categoria);
        Film getFilmById(String id);
        Film addFilm(Film f);
        Film deleteFilmById(String id);
        Film updateFilmById (Film nuovoFilm, String id);
        List<Film> getFilmByFormatoQuery(String formato);
        //List<Film> addListaFilms(String formato);
}
