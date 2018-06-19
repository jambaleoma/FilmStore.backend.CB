package com.javasampleapproach.couchbase.Gemma.service;

import com.javasampleapproach.couchbase.Exception.AlreadyExistException;
import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Gemma.model.Gemma;
import com.javasampleapproach.couchbase.Gemma.repository.GemmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("GemmaService")
public class GemmaServiceImpl implements GemmaService {

    @Autowired
    private GemmaRepository gemmaRepository;

    @Override
    public List<Gemma> getAllGemme() {
        List<Gemma> gemme = (List<Gemma>) gemmaRepository.findAll();
        if (gemme.size() == 0) {
            throw new NotFoundException("Nessuna Gemma Trovata");
        }
        return gemme;
    }

    @Override
    public List<Gemma> getAllGemmeByPrice(Integer price) {
        List<Gemma> gemme = new ArrayList<>();
        for (Gemma g : gemmaRepository.findAll()) {
            if (g.getPrice().equals(price)) {
                gemme.add(g);
            }
        }
        if (gemme.size() == 0) {
            throw new NotFoundException("Nessuna Gemma con Prezzo: " + price + " è stata Trovata");
        }
        return gemme;
    }

    @Override
    public Gemma getGemmaById(String id) {
        Gemma g =  gemmaRepository.findOne(id);
        if (g == null)
            throw new NotFoundException("Gemma con id: " + id + " NON Trovata");
        return g;
    }

    @Override
    public Gemma addGemma(Gemma g) {
        for (Gemma gemma : gemmaRepository.findAll()) {
            if (gemma.getId().equals(g.getId()))
                throw new AlreadyExistException("La Gemma con Id: " + g.getId() + " è già presente, Cambia l'ID se vuoi inserire la Gemma");
        }
        return gemmaRepository.save(g);
    }

    @Override
    public Gemma updateGemma(Gemma nuovaGemma, String id) {
        Gemma gemmaDaAggiornare =  this.getGemmaById(id);
        if (gemmaDaAggiornare == null)
            throw new NotFoundException("Gemma con id: " + id + " NON Trovata");
        return gemmaRepository.save(nuovaGemma);
    }

    @Override
    public Gemma deleteGemmaById(String id) {
        Gemma g = this.getGemmaById(id);
        gemmaRepository.delete(g);
        return g;
    }

}
