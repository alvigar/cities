package org.eurovision.cities;

import org.eurovision.cities.entity.City;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CitiesRepository extends PagingAndSortingRepository<City, Integer> {

}
