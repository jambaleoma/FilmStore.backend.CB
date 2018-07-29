package com.javasampleapproach.couchbase.SerieTV.service;

import com.javasampleapproach.couchbase.Exception.NotFoundException;
import com.javasampleapproach.couchbase.SerieTV.model.SerieTV;
import com.javasampleapproach.couchbase.SerieTV.repository.SerieTVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component("SerieTVService")
public class SerieTVServiceImpl implements SerieTVService{

    @Autowired
    private SerieTVRepository serieTVRepository;

    @Override
    public List<SerieTV> getAllSerieTVs() {
        List<SerieTV> serieTV_ALL = new ArrayList<>();
        List<SerieTV> serieTV_WEB = (List<SerieTV>) serieTVRepository.getSerieTVsByFormatoQuery("WEB");
        List<SerieTV> serieTV_FULLHD = (List<SerieTV>) serieTVRepository.getSerieTVsByFormatoQuery("FULL-HD");
        serieTV_ALL.addAll(serieTV_FULLHD);
        serieTV_ALL.addAll(serieTV_WEB);
        if (serieTV_ALL == null) {
            throw new NotFoundException("Nessuna SerieTV Trovata");
        }
        return serieTV_ALL;
    }

    @Override
    public List<SerieTV> getAllSerieTVByName(String nome) {
        return null;
    }

    @Override
    public SerieTV getSerieTVById(String id) {
        return null;
    }

    @Override
    public SerieTV addSerieTV(SerieTV stv) {
        return serieTVRepository.save(stv);
    }

    @Override
    public SerieTV deleteSerieTVById(String id) {
        return null;
    }

    @Override
    public List<SerieTV> getSerieTVsByFormatoQuery(String formato) {
        List<SerieTV> serieTVsByFormatoQuery = (List<SerieTV>) serieTVRepository.getSerieTVsByFormatoQuery(formato);
        if (serieTVsByFormatoQuery.size() == 0) {
            throw new NotFoundException("Nessuna SerieTV Trovata");
        }
        return serieTVsByFormatoQuery;
    }

    //Riempie il DB Couchbase Con le SerieTV nel disco di BackUp
    @Override
    public List<SerieTV> addListaSerieTVs(String formato) {
        //PC
        File from1080 = new File("H:\\SerieTV\\1080p");
        File fromDVDRip = new File("H:\\SerieTV\\WEBRip");
        //MAC
        //File from1080 = new File("/Volumes/TOSHIBA EXT/SerieTV/1080p");
        //File fromDVDRip = new File("/Volumes/TOSHIBA EXT/SerieTV/WEBRip");
        File[] f1080 = trovaFile(from1080);
        File[] fWEB = trovaFile(fromDVDRip);
        List<SerieTV> listaSerieTV = new ArrayList<>();
        if (formato.equals("FULL-HD")) {
            for (int i=0; i<f1080.length; i++) {
                listaSerieTV.add(this.addSerieTV(new SerieTV(f1080[i].getName(), formato)));
            }
        }
        if (formato.equals("WEB")) {
            for (int i=0; i<fWEB.length; i++) {
                listaSerieTV.add(this.addSerieTV(new SerieTV(fWEB[i].getName(), formato)));
            }
        }
        return listaSerieTV;
    }

    private static File[] trovaFile(File from) {
        File[] files = from.listFiles();
        return files;
    }
}
