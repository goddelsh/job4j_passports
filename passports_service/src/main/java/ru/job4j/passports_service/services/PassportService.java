package ru.job4j.passports_service.services;


import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.passports_service.models.Passport;
import ru.job4j.passports_service.repositories.PassportRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PassportService {

    @Value( "${replacePeriod}" )
    private Integer replacePeriod;

    private final PassportRepository passportRepository;

    public PassportService(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }


    public Passport save(@NonNull Passport passport) {
        return this.passportRepository.save(passport);
    }

    public Passport update(@NonNull Passport passport) {
        return this.passportRepository.save(passport);
    }

    public void delete(@NonNull Passport passport) {
        this.passportRepository.delete(passport);
    }

    public List<Passport> find() {
        return StreamSupport.stream(this.passportRepository.findAll().spliterator(), true)
                .collect(Collectors.toList());
    }

    public List<Passport> findBySeria(String seria) {
        return this.passportRepository.findBySeries(seria);
    }


    public List<Passport> findUnavaliabe() {
        return this.passportRepository.findUnavaliabe();
    }

    public List<Passport> findReplaceable() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -replacePeriod);
        cal.getTime();
        return this.passportRepository.findReplaceable(cal.getTime());
    }
}
