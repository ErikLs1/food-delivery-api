package com.foodDelivery.api.unitTests.service;

import com.foodDelivery.api.dto.VehicleDTO;
import com.foodDelivery.api.exception.VehicleNotFoundException;
import com.foodDelivery.api.mapper.VehicleMapper;
import com.foodDelivery.api.model.Vehicle;
import com.foodDelivery.api.model.enums.VehicleType;
import com.foodDelivery.api.repository.VehicleRepository;
import com.foodDelivery.api.service.impl.VehicleServiceImpl;
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
 * Unit tests to verify that the CRUD operations of the Vehicle service work as expected.
 */
@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Vehicle vehicle;
    private VehicleDTO vehicleDTO;

    /**
     * Common data set up before each test.
     */
    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setVehicleId(1L);
        vehicle.setVehicleType(VehicleType.BIKE);

        vehicleDTO = new VehicleDTO();
        vehicleDTO.setVehicleId(1L);
        vehicleDTO.setVehicleType(VehicleType.BIKE);
    }

    /**
     * Tests that a new Vehicle can be created.
     *
     * <p>
     *     It verifies that the mapper converts the DTO to an entity, the repository saves
     *     the entity, and the response entity contains the correct data.
     * </p>
     */
    @Test
    void testCreatVehicle() {
        when(vehicleMapper.toEntity(vehicleDTO)).thenReturn(vehicle);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toDTO(vehicle)).thenReturn(vehicleDTO);

        VehicleDTO result = vehicleService.create(vehicleDTO);

        assertNotNull(result);
        assertEquals(VehicleType.BIKE, result.getVehicleType());
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    /**
     * Test that existing Vehicle can be updated.
     *
     * <p>
     *     The test verifies that when Vehicle exists, after updating it returns updated DTO.
     * </p>
     */
    @Test
    void testUpdateVehicle() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(vehicleMapper.toDTO(any(Vehicle.class))).thenAnswer(invocation -> {
            Vehicle vehicle1 = invocation.getArgument(0);
            VehicleDTO dto = new VehicleDTO();
            dto.setVehicleId(vehicle1.getVehicleId());
            dto.setVehicleType(vehicle1.getVehicleType());
            return dto;
        });

        VehicleDTO updatedDTO = new VehicleDTO(1L, VehicleType.CAR);
        vehicle.setVehicleType(VehicleType.CAR);

        VehicleDTO result = vehicleService.update(1L, updatedDTO);

        assertNotNull(result);
        assertEquals(VehicleType.CAR, result.getVehicleType());
        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    /**
     * Test that updating Vehicle that does not exist throws a {@link VehicleNotFoundException}.
     */
    @Test
    void testUpdateVehicleNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());
        VehicleDTO updatedDTO = new VehicleDTO(1L, VehicleType.CAR);

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.update(1L, updatedDTO));
        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }


    /**
     * Tests that the Vehicle can be retrieved by the id.
     *
     * <p>
     *     The test verifies that the service returns the correct DTO.
     * </p>
     */
    @Test
    void testGetVehicleById() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.toDTO(vehicle)).thenReturn(vehicleDTO);

        VehicleDTO result = vehicleService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getVehicleId());
        assertEquals(VehicleType.BIKE, result.getVehicleType());
        verify(vehicleRepository, times(1)).findById(1L);
    }

    /**
     * Test that retrieving Vehicle by id that does not exist throws a {@link VehicleNotFoundException}.
     */
    @Test
    void testGetVehicleByIdWasNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.getById(1L));
        verify(vehicleRepository, times(1)).findById(1L);
    }

    /**
     * Tests that all Vehicles can be retrieved.
     *
     * <p>
     *     Verifies that the service return a list of VehicleDTO.
     * </p>
     */
    @Test
    void testGetAllVehicles() {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setVehicleId(2L);
        vehicle1.setVehicleType(VehicleType.CAR);

        VehicleDTO vehicleDTO1 = new VehicleDTO();
        vehicleDTO1.setVehicleId(2L);
        vehicleDTO1.setVehicleType(VehicleType.CAR);

        List<Vehicle> vehicles = List.of(vehicle, vehicle1);
        when(vehicleRepository.findAll()).thenReturn(vehicles);
        when(vehicleMapper.toDTO(vehicle)).thenReturn(vehicleDTO);
        when(vehicleMapper.toDTO(vehicle1)).thenReturn(vehicleDTO1);

        List<VehicleDTO> result = vehicleService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2L, result.get(1).getVehicleId());
        assertEquals(VehicleType.CAR, result.get(1).getVehicleType());

        verify(vehicleRepository, times(1)).findAll();
        verify(vehicleMapper, times(2)).toDTO(any());
    }

    /**
     * Tests that Vehicle can be deleted.
     *
     * <p>
     *     Verifies that deletion does not throw any exception and that repository method is called only once.
     * </p>
     */
    @Test
    void testDeleteVehicle() {
        when(vehicleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(vehicleRepository).deleteById(1L);

        assertDoesNotThrow(() -> vehicleService.delete(1L));
        verify(vehicleRepository, times(1)).existsById(1L);
        verify(vehicleRepository, times(1)).deleteById(1L);

    }

    /**
     * Tests that deleting Vehicle that does not exist throws {@link VehicleNotFoundException}.
     */
    @Test
    void testDeleteVehicleNotFound() {
        when(vehicleRepository.existsById(1L)).thenReturn(false);

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.delete(1L));
        verify(vehicleRepository, times(1)).existsById(1L);
        verify(vehicleRepository, never()).deleteById(30L);
    }
}
