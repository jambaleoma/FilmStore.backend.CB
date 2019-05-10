package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Film.model.Film;

import java.util.Comparator;

public class DataCreazioneComparatore implements Comparator<Film> {
    @Override
    public int compare(Film f1, Film f2) {
        return f2.getDataCreazione() < f1.getDataCreazione() ? -1 : f2.getDataCreazione() == f1.getDataCreazione() ? 0 : 1;
    }
}