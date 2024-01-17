package org.eurovision.cities;

import org.eurovision.cities.entity.CitiesResponse;
import org.eurovision.cities.entity.City;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/cities")
public class CitiesController {

    private final CitiesService service;

    public CitiesController(CitiesService service) {
        this.service = service;
    }

    @GetMapping("/queryByPage")
    @ResponseBody
    public CitiesResponse get(@RequestParam Integer page, @RequestParam  Integer size) {
        return this.service.query(page, size);
    }

    @GetMapping("/longestSequence")
    @ResponseBody
    public List<City> longestSequence(@RequestParam Integer limit) {
        return this.service.longestSequence(limit);
    }
}
