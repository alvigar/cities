package org.eurovision.cities.services;

import org.eurovision.cities.repositories.CitiesRepository;
import org.eurovision.cities.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CitiesService {

    private final CitiesRepository repository;

    public CitiesService(CitiesRepository repository) {
        this.repository = repository;
    }

    public Page<City> query(Pageable pageable, String filter) {
        if (filter != null) {
            return this.repository.findByNameStartingWith(pageable, filter);
        }
        return this.repository.findAll(pageable);
    }

    public List<City> longestSequence(Integer limit) {
        Page<City> page = this.repository.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "name")));
        List<City> cities = page.getContent();
        List<City> newCities = new ArrayList<>();
        int i = 0;
        while(i < cities.size() && newCities.size() < (cities.size() - i)) {
            List<City> sequence = getSequence(cities.subList(i, cities.size() - 1));
            if (sequence.size() > newCities.size()) {
                newCities = sequence;
            }
            i++;
        }
        return newCities;
    }

    private List<City> getSequence(List<City> cities) {
        List<City> newCities = new ArrayList<>();
        Integer lastId = 0 ;
        for (City city : cities) {
            if (lastId < city.getId()) {
                newCities.add(city);
                lastId = city.getId();
            }
        }
        return newCities;
    }
}
