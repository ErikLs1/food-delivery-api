package com.foodDelivery.api.unitTests.service;

import com.foodDelivery.api.dto.BaseFeeDTO;
import com.foodDelivery.api.exception.BaseFeeNotFoundException;
import com.foodDelivery.api.mapper.BaseFeeMapper;
import com.foodDelivery.api.model.BaseFee;
import com.foodDelivery.api.model.City;
import com.foodDelivery.api.model.Vehicle;
import com.foodDelivery.api.model.enums.VehicleType;
import com.foodDelivery.api.repository.BaseFeeRepository;
import com.foodDelivery.api.service.impl.BaseFeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests to verify that the CRUD operations of the BaseFee service work as expected.
 */
@ExtendWith(MockitoExtension.class)
public class BaseFeeServiceTest {

    @Mock
    private BaseFeeRepository baseFeeRepository;

    @Mock
    private BaseFeeMapper baseFeeMapper;

    @InjectMocks
    private BaseFeeServiceImpl baseFeeService;

    private BaseFee baseFee;
    private BaseFeeDTO baseFeeDTO;

    /**
     * Common data set up before each test.
     */
    @BeforeEach
    void setUp() {
        City city = new City();
        city.setCityId(5L);
        city.setCityName("Tallinn");
        city.setStationName("Tallinn-Harku");
        city.setWmoCode("26038");

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(6L);
        vehicle.setVehicleType(VehicleType.BIKE);

        baseFee = new BaseFee();
        baseFee.setBaseFeeId(1L);
        baseFee.setVehicleFee(3.00);
        baseFee.setCity(city);
        baseFee.setVehicle(vehicle);

        baseFeeDTO = new BaseFeeDTO();
        baseFeeDTO.setBaseFeeId(1L);
        baseFeeDTO.setVehicleFee(3.00);
        baseFeeDTO.setCityId(5L);
        baseFeeDTO.setVehicleId(6L);
    }

    /**
     * Tests that a new BaseFee can be created.
     *
     * <p>
     *     It verifies that the mapper converts the DTO to an entity, the repository saves
     *     the entity, and the response entity contains the correct data.
     * </p>
     */
    @Test
    void testCrateBaseFee() {
        when(baseFeeMapper.toEntity(baseFeeDTO)).thenReturn(baseFee);
        when(baseFeeRepository.save(baseFee)).thenReturn(baseFee);
        when(baseFeeMapper.toDTO(baseFee)).thenReturn(baseFeeDTO);

        BaseFeeDTO result = baseFeeService.create(baseFeeDTO);

        assertNotNull(result);
        assertEquals(3.00, result.getVehicleFee());
        assertEquals(5L, result.getCityId());
        assertEquals(6L, result.getVehicleId());
        verify(baseFeeRepository, times(1)).save(baseFee);
    }

    /**
     * Test that existing BaseFee can be updated.
     *
     * <p>
     *     The test verifies that when BaseFee exists, after updating it returns updated DTO.
     * </p>
     */
    @Test
    void testUpdateBaseFee() {
        when(baseFeeRepository.findById(1L)).thenReturn(Optional.of(baseFee));
        when(baseFeeRepository.save(any(BaseFee.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(baseFeeMapper.toDTO(any(BaseFee.class))).thenAnswer(invocation -> {
            BaseFee updated = invocation.getArgument(0);
            BaseFeeDTO dto = new BaseFeeDTO();
            dto.setBaseFeeId(updated.getBaseFeeId());
            dto.setCityId(updated.getCity().getCityId());
            dto.setVehicleId(updated.getVehicle().getVehicleId());
            dto.setVehicleFee(5.00);
            return dto;
        });

        BaseFeeDTO updatedDTO = new BaseFeeDTO(1L, baseFeeDTO.getCityId(), baseFeeDTO.getVehicleId(), 5.00);
        baseFee.setVehicleFee(updatedDTO.getVehicleFee());

        BaseFeeDTO result = baseFeeService.update(1L, updatedDTO);
        assertNotNull(result);
        assertEquals(5.00, result.getVehicleFee());
        verify(baseFeeRepository, times(1)).findById(1L);
        verify(baseFeeRepository, times(1)).save(baseFee);
    }

    /**
     * Test that updating BaseFee that does not exist throws a {@link BaseFeeNotFoundException}.
     */
    @Test
    void testUpdateBaseFeeNotFound() {
        when(baseFeeRepository.findById(1L)).thenReturn(Optional.empty());
        BaseFeeDTO updatedDTO = new BaseFeeDTO(1L, baseFeeDTO.getCityId(), baseFeeDTO.getVehicleId(), 5.00);
        assertThrows(BaseFeeNotFoundException.class, () -> baseFeeService.update(1L, updatedDTO));
        verify(baseFeeRepository, times(1)).findById(1L);
        verify(baseFeeRepository, never()).save(any(BaseFee.class));
    }

    /**
     * Tests that the BaseFee can be retrieved by the id.
     *
     * <p>
     *     The test verifies that the service returns the correct DTO.
     * </p>
     */
    @Test
    void testGetBaseFeeById() {
        when(baseFeeRepository.findById(1L)).thenReturn(Optional.of(baseFee));
        when(baseFeeMapper.toDTO(baseFee)).thenReturn(baseFeeDTO);

        BaseFeeDTO result = baseFeeService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getBaseFeeId());
        assertEquals(3.00, result.getVehicleFee());
        verify(baseFeeRepository, times(1)).findById(1L);
    }

    /**
     * Test that retrieving BaseFee by id that does not exist throws a {@link BaseFeeNotFoundException}.
     */
    @Test
    void testGetBaseFeeByIdNotFound() {
        when(baseFeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BaseFeeNotFoundException.class, () -> baseFeeService.getById(1L));
        verify(baseFeeRepository, times(1)).findById(1L);
    }

    /**
     * Tests that all BaseFees can be retrieved.
     *
     * <p>
     *     Verifies that the service return a list of BaseFeeDTO.
     * </p>
     */
    @Test
    void testGetAllBaseFees() {
        List<BaseFee> baseFees = List.of(baseFee);
        when(baseFeeRepository.findAll()).thenReturn(baseFees);
        when(baseFeeMapper.toDTO(baseFee)).thenReturn(baseFeeDTO);

        List<BaseFeeDTO> result = baseFeeService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(baseFeeRepository, times(1)).findAll();
    }

    /**
     * Tests that BaseFee can be deleted.
     *
     * <p>
     *     Verifies that deletion does not throw any exception and that repository method is called only once.
     * </p>
     */
    @Test
    void testDeleteBaseFee() {
        when(baseFeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(baseFeeRepository).deleteById(1L);

        assertDoesNotThrow(() -> baseFeeService.delete(1L));
        verify(baseFeeRepository, times(1)).existsById(1L);
        verify(baseFeeRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests that deleting BaseFee that does not exist throws {@link BaseFeeNotFoundException}.
     */
    @Test
    void testDeleteBaseFeeNotFound() {
        when(baseFeeRepository.existsById(1L)).thenReturn(false);

        assertThrows(BaseFeeNotFoundException.class, () -> baseFeeService.delete(1L));
        verify(baseFeeRepository, times(1)).existsById(1L);
        verify(baseFeeRepository, never()).deleteById(40L);
    }
}
