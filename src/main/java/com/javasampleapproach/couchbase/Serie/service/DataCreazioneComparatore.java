package com.javasampleapproach.couchbase.Serie.service;

import com.javasampleapproach.couchbase.Serie.model.Serie;
import java.util.Comparator;

public class DataCreazioneComparatore implements Comparator<Serie> {
    @Override
    public int compare(Serie f1, Serie f2) {
        return f2.getDataCreazione() < f1.getDataCreazione() ? -1 : f2.getDataCreazione() == f1.getDataCreazione() ? 0 : 1;
    }
}