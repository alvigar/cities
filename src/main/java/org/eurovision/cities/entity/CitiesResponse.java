package org.eurovision.cities.entity;

import java.util.List;

public class CitiesResponse {
    private List<City> content;
    private Integer totalPages;
    private Long totalElements;

    public CitiesResponse() {}

    public CitiesResponse(List<City> content, Integer totalPages, Long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<City> getContent() {
        return content;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }
}
