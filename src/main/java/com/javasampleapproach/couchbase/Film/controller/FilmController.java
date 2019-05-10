package com.javasampleapproach.couchbase.Film.controller;

import com.javasampleapproach.couchbase.Film.model.Film;
import com.javasampleapproach.couchbase.Film.service.FilmService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rest/films")
public class FilmController {

    @Autowired
    private FilmService filmsService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllFilms() {
        try {
            List<Film> films = this.filmsService.getAllFilms();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Film", "--- OK --- Lista Film Trovata Con Successo").body(films);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/allNewFilms/{numeroNuoviFilm}")
    private ResponseEntity getAllNewFilms(@PathVariable String numeroNuoviFilm) {
        try {
            List<Film> films = this.filmsService.getAllNewFilms(Integer.parseInt(numeroNuoviFilm));
            return ResponseEntity.status(HttpStatus.OK).header("Lista Nuovi Film", "--- OK --- Lista Nuovi Film Trovata Con Successo").body(films);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/byName/{nome}")
    private ResponseEntity getAllFilmsByName(@PathVariable String nome) {
        try {
            List<Film> films = this.filmsService.getAllFilmsByName(nome);
            return ResponseEntity.status(HttpStatus.OK).header("Lista Films per Nome", "--- OK --- Lista Films per Nome Trovata Con Successo").body(films);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/byCategory/{category}")
    private ResponseEntity getFilmsByCategory(@PathVariable String category) {
        try {
            List<Film> films = this.filmsService.getAllFilmsByCategory(category);
            return ResponseEntity.status(HttpStatus.OK).header("Lista Films per Categoria", "--- OK --- Lista Films per Categoria Trovata Con Successo", "Numero Film Trovati: " + films.size()).body(films);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    private ResponseEntity getFilmById(@PathVariable String id) {
        try {
            Film filmById = this.filmsService.getFilmById(id);
            return ResponseEntity.status(HttpStatus.OK).header("Ricerca Film", "--- OK --- Film Trovato Con Successo").body(filmById);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/formato/{formato}")
    private ResponseEntity getFilmByFormatoQuery(@PathVariable String formato) {
        try {
            List<Film> filmByIdQuery = this.filmsService.getFilmByFormatoQuery(formato);
            return ResponseEntity.status(HttpStatus.OK).header("Lista Film per Formato", "--- OK --- Lista Film per Formato Trovata Con Successo").body(filmByIdQuery);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertFilm")
    public ResponseEntity addFilm(@RequestBody Film f) {
        try {
            filmsService.addFilm(f);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Film", "--- OK --- Film Creato Con Successo").body(getAllFilms().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/upDateFilmById/{id}")
    private ResponseEntity upDateFilmById(@RequestBody Film nuovoFilm, @PathVariable String id) {
        try {
            filmsService.updateFilmById(nuovoFilm, id);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Film", "--- OK --- Film Aggiornato Con Successo").body(getAllFilms().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteFilmById/{id}")
    private ResponseEntity deleteFilmById(@PathVariable String id) {
        try {
            filmsService.deleteFilmById(id);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Film", "--- OK --- Film Eliminato Con Successo").body("Il Film con Id: " + id + " è stato Eliminato con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/locandina/saveLocandinaImage/{filmId}")
    private ResponseEntity saveCustomerImage(@RequestParam("filmLocandina") MultipartFile file, @PathVariable String filmId) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Locandina Film", "Non è stato trovato nessun File da caricare").body("Errore");
        }
        try {
            byte[] bytes = file.getBytes();
            StringBuilder sb = new StringBuilder();
            sb.append("data:image/png;base64,");
            sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(bytes, false)));
            Film filmToAddLocandina = filmsService.getFilmById(filmId);
            filmToAddLocandina.setLocandina(sb.toString());
            filmsService.updateFilmById(filmToAddLocandina, filmId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).header("Locandina Film", "Locandina Film Aggiornata con Successo").body("OK");
    }

    /*
    @PostMapping(value = "/insertLocalListFilm/{formato}")
    public ResponseEntity addFilmsList(@PathVariable String formato) {
        try {
            List<Film> listaFilm = filmsService.addListaFilms(formato);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Lista Film", "--- OK --- Lista Film Creata Con Successo").body(listaFilm);
        } catch (Exception e) {
            throw e;
        }
    }
    */

}
