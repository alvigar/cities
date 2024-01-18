package org.eurovision.cities.repositories;

import org.eurovision.cities.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CitiesRepository extends PagingAndSortingRepository<City, Integer> {

    Page<City> findByNameStartingWith(Pageable pageable, String name);
}
