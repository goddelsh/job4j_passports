package ru.job4j.passports_client.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.passports_client.models.Passport;
import ru.job4j.passports_client.services.PassportsAPIService;

import java.util.List;

@RestController
@RequestMapping("/")
public class PassportsAPIController {
    final private PassportsAPIService passportsAPIService;

    public PassportsAPIController(PassportsAPIService passportsAPIService) {
        this.passportsAPIService = passportsAPIService;
    }


    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        return new ResponseEntity<>(this.passportsAPIService.save(passport), HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity<Passport> update(@RequestParam Integer id, @RequestBody Passport passport) {
        if (id == null || passport == null) {
            throw new IllegalStateException();
        }
        passport.setId(id);
        return new ResponseEntity<>(this.passportsAPIService.update(passport), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        if (id == null) {
            throw new IllegalStateException();
        }
        this.passportsAPIService.delete(new Passport(id));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @GetMapping("/find")
    public List<Passport> find(@RequestParam (required = false)  String seria) {
        return seria == null || seria.isEmpty() ?
                this.passportsAPIService.find() : this.passportsAPIService.findBySeria(seria);
    }

    @GetMapping("/unavaliabe")
    public List<Passport> unavaliabe() {
        return this.passportsAPIService.findUnavaliabe();
    }

    @GetMapping("/find-replaceable")
    public List<Passport> findReplaceable() {
        return this.passportsAPIService.findReplaceable();
    }
}
