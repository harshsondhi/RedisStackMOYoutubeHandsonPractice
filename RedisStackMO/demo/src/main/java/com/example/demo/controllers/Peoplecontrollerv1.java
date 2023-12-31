package com.example.demo.controllers;

import com.example.demo.models.Person;
import com.example.demo.models.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/people")
public class Peoplecontrollerv1 {

    @Autowired
    PeopleRepository repo;

    @GetMapping("all")
    Iterable<Person> all(){
        return repo.findAll();
    }

    @GetMapping("{id}")
    Optional<Person> byId(@PathVariable String id) {
        var x = repo.findById(id);
        return repo.findById(id);
    }

    @GetMapping("age_between")
    Iterable<Person> byAgeBetween( //
                                   @RequestParam("min") int min, //
                                   @RequestParam("max") int max) {
        return repo.findByAgeBetween(min, max);
    }

    @GetMapping("name")
    Iterable<Person> byFirstNameAndLastName(@RequestParam("first") String firstName, //
                                            @RequestParam("last") String lastName) {
        return repo.findByFirstNameAndLastName(firstName, lastName);
    }


    @GetMapping("homeloc")
    Iterable<Person> byHomeLoc(//
                               @RequestParam("lat") double lat, //
                               @RequestParam("lon") double lon, //
                               @RequestParam("d") double distance) {
        return repo.findByHomeLoc(new Point(lon, lat), new Distance(distance, Metrics.MILES));
    }

    @GetMapping("statement")
    Iterable<Person> byPersonalStatement(@RequestParam("q") String q) {
        return repo.searchByPersonalStatement(q);
    }

    @GetMapping("city")
    Iterable<Person> byCity(@RequestParam("city") String city) {
        return repo.findByAddress_City(city);
    }

    @GetMapping("skills")
    Iterable<Person> byAnySkills(@RequestParam("skills") Set<String> skills) {
        return repo.findBySkills(skills);
    }

    @GetMapping("skills/all")
    Iterable<Person> byAllSkills(@RequestParam("skills") Set<String> skills) {
        return repo.findBySkillsContainingAll(skills);
    }

}
