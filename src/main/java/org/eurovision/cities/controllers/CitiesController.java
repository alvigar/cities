package org.eurovision.cities.controllers;

import org.eurovision.cities.entities.CitiesResponse;
import org.eurovision.cities.entities.City;
import org.eurovision.cities.services.CitiesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

    private final CitiesService service;

    public CitiesController(CitiesService service) {
        this.service = service;
    }

    @GetMapping("/queryByPage")
    @ResponseBody
    public CitiesResponse get(@RequestParam(required = false) String filter, Pageable pageable) {
        Page<City> pageCities = this.service.query(pageable, filter);
        return new CitiesResponse(pageCities.getContent(), pageCities.getTotalPages(), pageCities.getTotalElements());
    }

    @GetMapping("/longestSequence")
    @ResponseBody
    public List<City> longestSequence(@RequestParam Integer limit) {
        return this.service.longestSequence(limit);
    }
}
