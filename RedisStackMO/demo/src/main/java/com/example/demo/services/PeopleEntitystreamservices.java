package com.example.demo.services;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.example.demo.models.Person;
import com.example.demo.models.Person$;
import com.redis.om.spring.search.stream.EntityStream;

import redis.clients.jedis.search.aggr.SortedField.SortOrder;

@Service
public class PeopleEntitystreamservices {
    @Autowired
    EntityStream entityStream;

    public Iterable<Person> findByAgeBetween(int minAge, int maxAge) {
        return entityStream
                .of(Person.class)
                .filter(Person$.AGE.between(minAge, maxAge))
                .sorted(Person$.AGE, SortOrder.ASC)
                .collect(Collectors.toList());
    }


    public Iterable<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        return entityStream
                .of(Person.class)
                .filter(Person$.FIRST_NAME.eq(firstName))
                .filter(Person$.LAST_NAME.eq(lastName))
                .collect(Collectors.toList());
    }

    public Iterable<Person> findByHomeLoc(Point point, Distance distance) {
        return entityStream
                .of(Person.class)
                .filter(Person$.HOME_LOC.near(point, distance))
                .collect(Collectors.toList());
    }


    public Iterable<Person> searchByPersonalStatement(String text) {
        return entityStream
                .of(Person.class)
                .filter(Person$.PERSONAL_STATEMENT.eq(text))
                .collect(Collectors.toList());
    }


}
