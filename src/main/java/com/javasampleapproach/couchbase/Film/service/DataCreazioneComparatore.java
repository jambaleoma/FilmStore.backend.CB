package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Film.model.Film;

import java.util.Comparator;

public class DataCreazioneComparatore implements Comparator<Film> {
    @Override
    public int compare(Film f1, Film f2) {
        int i = 0;
        if (f2.getDataCreazione() < f1.getDataCreazione()) {
            i = -1;
        } else if (f2.getDataCreazione() > f1.getDataCreazione()) {
            i = 1;
        }
        return i;
    }
}