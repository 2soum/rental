package com.example.rental.web;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rental.entity.Car;
import com.example.rental.service.RentalService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for RentalWebService REST Controller
 * Tests all REST endpoints using Spring MockMVC
 */
@WebMvcTest(RentalWebService.class)
class RentalWebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Car car1;
    private Car car2;
    private List<Car> cars;

    @BeforeEach
    void setUp() {
        car1 = new Car("ABC123", "Toyota");
        car2 = new Car("XYZ789", "Honda");
        cars = Arrays.asList(car1, car2);
    }

    // ============ GET /cars ============

    @Test
    void testGetAllCars_Success() throws Exception {
        when(rentalService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].plate", equalTo("ABC123")))
                .andExpect(jsonPath("$[0].brand", equalTo("Toyota")))
                .andExpect(jsonPath("$[1].plate", equalTo("XYZ789")))
                .andExpect(jsonPath("$[1].brand", equalTo("Honda")));

        verify(rentalService, times(1)).getAllCars();
    }

    @Test
    void testGetAllCars_EmptyList() throws Exception {
        when(rentalService.getAllCars()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(rentalService, times(1)).getAllCars();
    }

    // ============ GET /cars/{plateNumber} ============

    @Test
    void testGetCarByPlate_Success() throws Exception {
        when(rentalService.getCarByPlate("ABC123")).thenReturn(Optional.of(car1));

        mockMvc.perform(get("/cars/ABC123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plate", equalTo("ABC123")))
                .andExpect(jsonPath("$.brand", equalTo("Toyota")))
                .andExpect(jsonPath("$.rented", equalTo(false)));

        verify(rentalService, times(1)).getCarByPlate("ABC123");
    }

    @Test
    void testGetCarByPlate_NotFound() throws Exception {
        when(rentalService.getCarByPlate("INVALID")).thenReturn(Optional.empty());

        mockMvc.perform(get("/cars/INVALID")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(rentalService, times(1)).getCarByPlate("INVALID");
    }

    // ============ PUT /cars/{plateNumber}?rent=true ============

    @Test
    void testRentCar_Success() throws Exception {
        when(rentalService.getCarByPlate("ABC123")).thenReturn(Optional.of(car1));
        when(rentalService.rentCar("ABC123")).thenReturn(true);

        mockMvc.perform(put("/cars/ABC123")
                .param("rent", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("rented successfully")));

        verify(rentalService, times(1)).getCarByPlate("ABC123");
        verify(rentalService, times(1)).rentCar("ABC123");
    }

    @Test
    void testRentCar_AlreadyRented() throws Exception {
        when(rentalService.getCarByPlate("ABC123")).thenReturn(Optional.of(car1));
        when(rentalService.rentCar("ABC123")).thenReturn(false);

        mockMvc.perform(put("/cars/ABC123")
                .param("rent", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("already rented")));

        verify(rentalService, times(1)).getCarByPlate("ABC123");
        verify(rentalService, times(1)).rentCar("ABC123");
    }

    @Test
    void testRentCar_NotFound() throws Exception {
        when(rentalService.getCarByPlate("INVALID")).thenReturn(Optional.empty());

        mockMvc.perform(put("/cars/INVALID")
                .param("rent", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound());

        verify(rentalService, times(1)).getCarByPlate("INVALID");
    }

    // ============ PUT /cars/{plateNumber}?rent=false ============

    @Test
    void testReturnCar_Success() throws Exception {
        car1.setRented(true);
        when(rentalService.getCarByPlate("ABC123")).thenReturn(Optional.of(car1));
        when(rentalService.returnCar("ABC123")).thenReturn(true);

        mockMvc.perform(put("/cars/ABC123")
                .param("rent", "false")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("returned successfully")));

        verify(rentalService, times(1)).getCarByPlate("ABC123");
        verify(rentalService, times(1)).returnCar("ABC123");
    }

    @Test
    void testReturnCar_NotRented() throws Exception {
        when(rentalService.getCarByPlate("ABC123")).thenReturn(Optional.of(car1));
        when(rentalService.returnCar("ABC123")).thenReturn(false);

        mockMvc.perform(put("/cars/ABC123")
                .param("rent", "false")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("not rented")));

        verify(rentalService, times(1)).getCarByPlate("ABC123");
        verify(rentalService, times(1)).returnCar("ABC123");
    }

    @Test
    void testReturnCar_NotFound() throws Exception {
        when(rentalService.getCarByPlate("INVALID")).thenReturn(Optional.empty());

        mockMvc.perform(put("/cars/INVALID")
                .param("rent", "false")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound());

        verify(rentalService, times(1)).getCarByPlate("INVALID");
    }

    // ============ Error Cases ============

    @Test
    void testUpdateCarRentalStatus_NoParameter() throws Exception {
        when(rentalService.getCarByPlate("ABC123")).thenReturn(Optional.of(car1));

        mockMvc.perform(put("/cars/ABC123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());

        verify(rentalService, times(1)).getCarByPlate("ABC123");
    }
}
