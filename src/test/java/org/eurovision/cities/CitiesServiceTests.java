package org.eurovision.cities;

import org.eurovision.cities.entities.City;
import org.eurovision.cities.repositories.CitiesRepository;
import org.eurovision.cities.services.CitiesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CitiesServiceTests {
    private final static Integer PAGE_SIZE = 10;
    private final static Integer PAGE_NUMBER = 1;
    private final static List<City> CITES_LIST = Arrays.asList(
            new City(233, "Abadan"),
            new City(67, "Abidian"),
            new City(157, "Accra"),
            new City(262, "Ad-Damman"),
            new City(255, "Adana"),
            new City(75, "Addis Ababa"),
            new City(297, "Adelaide"),
            new City(124, "Agadir"),
            new City(197, "Agra"),
            new City(43, "Ahmadabad"));

    @Mock
    private CitiesRepository repository;
    @InjectMocks
    private CitiesService service;

    @Test
    public void findAllTest() {
        Page mockedPage = mock(Page.class);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getTotalElements()).thenReturn(300L);
        when(mockedPage.getContent()).thenReturn(CITES_LIST);
        when(repository.findAll(PageRequest.of(PAGE_NUMBER, PAGE_SIZE))).thenReturn(mockedPage);
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        Page<City> response = service.query(pageable, null);

        assertThat(response.getTotalElements()).isEqualTo(300L);
        assertThat(response.getTotalPages()).isEqualTo(10);
        assertThat(response.getContent()).isEqualTo(CITES_LIST);
    }
}
