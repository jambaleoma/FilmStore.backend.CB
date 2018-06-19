package com.javasampleapproach.couchbase.Gemma.service;

import com.javasampleapproach.couchbase.Gemma.model.Gemma;

import java.util.List;

public interface GemmaService {

    List<Gemma> getAllGemme();
    List<Gemma> getAllGemmeByPrice(Integer price);
    Gemma getGemmaById(String id);
    Gemma addGemma(Gemma g);
    Gemma updateGemma (Gemma NuovaGemma, String id);
    Gemma deleteGemmaById(String id);
}
