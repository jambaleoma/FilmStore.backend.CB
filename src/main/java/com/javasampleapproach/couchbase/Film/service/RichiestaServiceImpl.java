package com.javasampleapproach.couchbase.Film.service;

import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.Film.model.Richiesta;
import com.javasampleapproach.couchbase.Film.repository.RichiestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component("RichiestaService")
public class RichiestaServiceImpl implements RichiestaService {

    private static final Logger LOGGER = Logger.getLogger(RichiestaServiceImpl.class.getName());

    @Autowired
    private RichiestaRepository richiestaRepository;

    @Override
    public List<Richiesta> getAllRichieste() {
        List<Richiesta> richieste = (List<Richiesta>) richiestaRepository.findAll();
        if (richieste == null) {
            throw new NotFoundException("Nessuna Richiesta Trovata");
        }
        StringBuilder listCustomer = new StringBuilder();
        listCustomer.append("\nLista Richieste:\n");
        for (Richiesta r : richieste) {
            listCustomer.append("Titolo Film Richiesto: " + r.getTitoloFilmRichiesto() + " Nome del Richiedente: " + r.getNomeCliente() + "\n");
        }
        LOGGER.info(listCustomer.toString());
        LOGGER.info("\nNumero Richieste Totali: " + richieste.size());
        return richieste;
    }

    @Override
    public List<Richiesta> getAllRichiesteByNomeCliente(String nomeCliente) {
        List<Richiesta> richiesteByIdCliente = richiestaRepository.getRichiesteByNomeQuery(nomeCliente);
        if (richiesteByIdCliente.size() == 0) {
            throw new NotFoundException("Nessuna Richiesta Trovata");
        }
        return richiesteByIdCliente;
    }

    @Override
    public Richiesta getRichiestaById(String id) {
        Richiesta r = richiestaRepository.findOne(id);
        if (r == null)
            throw new NotFoundException("Richiesta con id: " + id + " NON Trovata");
        return r;
    }

    @Override
    public Richiesta addRichiesta(Richiesta r) {
        return richiestaRepository.save(r);
    }

    @Override
    public Richiesta updateRichiesta(Richiesta nuovaRichiesta, String id) {
        if (this.richiestaRepository.exists(id)) {
            Richiesta r = this.getRichiestaById(id);
            r.setTitoloFilmRichiesto(nuovaRichiesta.getTitoloFilmRichiesto());
            r.setFormatoFilmRichiesto(nuovaRichiesta.getFormatoFilmRichiesto());
            r.setDataInserimento(nuovaRichiesta.getDataInserimento());
            r.setNomeCliente(nuovaRichiesta.getNomeCliente());
            r.setStato(nuovaRichiesta.getStato());
            r.setNote(nuovaRichiesta.getNote());
            this.richiestaRepository.getCouchbaseOperations().update(r);
            return r;
        } else {
            throw new NotFoundException("Richiesta NON Aggiornata");
        }
    }

    @Override
    public Richiesta deleteRichiestaById(String id) {
        Richiesta r = this.getRichiestaById(id);
        richiestaRepository.delete(r);
        return r;
    }

    @Override
    public ArrayList getRichiesteYearForStatistiche() {
        return new ArrayList<>(getRichiesteForStatisticsMethod().keySet());
    }

    @Override
    public ArrayList getRichiesteForStatistiche(String year) {
        return new ArrayList<>(getRichiesteForStatisticsMethod().get(year).values());
    }

    @Override
    public Map<String, Map<String, Integer>> getRichiesteForStatisticsMethod() {
        Map<String, Map<String, Integer>> anno2_mese2richieste = new LinkedHashMap<>();
        for (Richiesta r : richiestaRepository.findAll()) {
            String[] dataRichiesta = r.getDataInserimento().split(" ");
            String anno = dataRichiesta[3];
            String mese = dataRichiesta[2];
            if (!anno2_mese2richieste.containsKey(anno)) {
                anno2_mese2richieste.put(anno, createYear());
            } else {
                anno2_mese2richieste.put(anno, anno2_mese2richieste.get(anno));
            }
            anno2_mese2richieste.get(anno).put(mese, anno2_mese2richieste.get(anno).get(mese) + 1);
        }
        LOGGER.info(anno2_mese2richieste.get("2018").toString());
        LOGGER.info(anno2_mese2richieste.get("2017").toString());
        LOGGER.info(anno2_mese2richieste.keySet().toString());
        return anno2_mese2richieste;
    }

    private Map<String, Integer> createYear() {
        Map<String, Integer> mese2Richieste = new LinkedHashMap<>();
        String[] mesiAnno = new String[12];
        mesiAnno[0] = "gennaio";
        mesiAnno[1] = "febbraio";
        mesiAnno[2] = "marzo";
        mesiAnno[3] = "aprile";
        mesiAnno[4] = "maggio";
        mesiAnno[5] = "giugno";
        mesiAnno[6] = "luglio";
        mesiAnno[7] = "agosto";
        mesiAnno[8] = "settembre";
        mesiAnno[9] = "ottobre";
        mesiAnno[10] = "novembre";
        mesiAnno[11] = "dicembre";
        for (int i = 0; i < mesiAnno.length; i++) {
            mese2Richieste.put(mesiAnno[i], 0);
        }
        return mese2Richieste;
    }
}
