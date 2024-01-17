package org.eurovision.cities;

import org.eurovision.cities.entity.CitiesResponse;
import org.eurovision.cities.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public CitiesResponse query(Integer pageNumber, Integer pageSize) {
        Page<City> page = this.repository.findAll(PageRequest.of(pageNumber, pageSize));
        return new CitiesResponse(page.getContent(), page.getTotalPages(), page.getTotalElements());
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
