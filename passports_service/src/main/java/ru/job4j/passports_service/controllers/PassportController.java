package ru.job4j.passports_service.controllers;


import org.hibernate.procedure.ParameterMisuseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.passports_service.models.Passport;
import ru.job4j.passports_service.services.PassportService;

import java.util.List;

@RestController
@RequestMapping("/")
public class PassportController {
    final private PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        return new ResponseEntity<>(this.passportService.save(passport), HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity<Passport> update(@RequestParam Integer id, @RequestBody Passport passport) {
        if (id == null || passport == null) {
            throw new IllegalStateException();
        }
        passport.setId(id);
        return new ResponseEntity<>(this.passportService.update(passport), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        if (id == null) {
            throw new IllegalStateException();
        }
        this.passportService.delete(new Passport(id));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @GetMapping("/find")
//    public List<Passport> find() {
//        return this.passportService.find();
//    }

    @GetMapping("/find")
    public List<Passport> find(@RequestParam String seria) {
        return seria == null || seria.isEmpty() ?
                this.passportService.find() : this.passportService.findBySeria(seria);
    }

    @GetMapping("/unavaliabe")
    public List<Passport> unavaliabe() {
        return this.passportService.findUnavaliabe();
    }

    @GetMapping("/find-replaceable")
    public List<Passport> findReplaceable() {
        return this.passportService.findReplaceable();
    }
}
