package com.foodDelivery.api.unitTests.service;

import com.foodDelivery.api.dto.ConditionsDTO;
import com.foodDelivery.api.exception.ConditionNotFoundException;
import com.foodDelivery.api.mapper.ConditionsMapper;
import com.foodDelivery.api.model.Conditions;
import com.foodDelivery.api.model.Vehicle;
import com.foodDelivery.api.model.enums.ConditionType;
import com.foodDelivery.api.model.enums.VehicleType;
import com.foodDelivery.api.repository.ConditionsRepository;
import com.foodDelivery.api.service.impl.ConditionsServiceImpl;
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

@ExtendWith(MockitoExtension.class)
public class ConditionsServiceTest {

    @Mock
    private ConditionsRepository conditionsRepository;

    @Mock
    private ConditionsMapper conditionsMapper;

    @InjectMocks
    private ConditionsServiceImpl conditionsService;

    private Conditions conditions;
    private ConditionsDTO conditionsDTO;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setVehicleId(3L);
        vehicle.setVehicleType(VehicleType.SCOOTER);

        conditions = new Conditions();
        conditions.setConditionsId(1L);
        conditions.setVehicle(vehicle);
        conditions.setConditionType(ConditionType.PHENOMENON);
        conditions.setMinValue(-10.0);
        conditions.setMaxValue(0.0);
        conditions.setPhenomenon("Light rain");
        conditions.setConditionFee(1.00);
        conditions.setUsageForbidden(false);

        conditionsDTO = new ConditionsDTO();
        conditionsDTO.setConditionsId(1L);
        conditionsDTO.setVehicleId(3L);
        conditionsDTO.setConditionType(ConditionType.PHENOMENON);
        conditionsDTO.setMinValue(-10.0);
        conditionsDTO.setMaxValue(0.0);
        conditionsDTO.setPhenomenon("Light rain");
        conditionsDTO.setConditionFee(1.00);
        conditionsDTO.setUsageForbidden(false);
    }

    @Test
    void testCreateConditions() {
        when(conditionsMapper.toEntity(conditionsDTO)).thenReturn(conditions);
        when(conditionsRepository.save(conditions)).thenReturn(conditions);
        when(conditionsMapper.toDTO(conditions)).thenReturn(conditionsDTO);

        ConditionsDTO result = conditionsService.create(conditionsDTO);

        assertNotNull(result);
        assertEquals(1.00, result.getConditionFee());
        assertEquals(3L, result.getVehicleId());
        verify(conditionsRepository, times(1)).save(conditions);
    }

    @Test
    void testUpdateConditions() {
        when(conditionsRepository.findById(1L)).thenReturn(Optional.of(conditions));
        when(conditionsRepository.save(any(Conditions.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(conditionsMapper.toDTO(any(Conditions.class))).thenAnswer(invocation -> {
            Conditions updated = invocation.getArgument(0);
            ConditionsDTO dto = new ConditionsDTO();
            dto.setConditionsId(updated.getConditionsId());
            dto.setVehicleId(updated.getVehicle().getVehicleId());
            dto.setConditionType(updated.getConditionType());
            dto.setMinValue(updated.getMinValue());
            dto.setMaxValue(updated.getMaxValue());
            dto.setPhenomenon(updated.getPhenomenon());
            dto.setConditionFee(updated.getConditionFee());
            dto.setUsageForbidden(updated.getUsageForbidden());
            return dto;
        });

        ConditionsDTO updateDTO = new ConditionsDTO(1L, conditionsDTO.getVehicleId(),
                conditionsDTO.getConditionType(), conditionsDTO.getMinValue(), conditionsDTO.getMaxValue(),
                conditionsDTO.getPhenomenon(), 0.50, conditionsDTO.getUsageForbidden());

        conditions.setConditionFee(updateDTO.getConditionFee());

        ConditionsDTO result = conditionsService.update(1L, updateDTO);

        assertNotNull(result);
        assertEquals(0.50, result.getConditionFee());
        verify(conditionsRepository, times(1)).findById(1L);
        verify(conditionsRepository, times(1)).save(conditions);
    }

    @Test
    void testUpdateConditionsNotFound() {
        when(conditionsRepository.findById(1L)).thenReturn(Optional.empty());
        ConditionsDTO updateDTO = new ConditionsDTO(1L, conditionsDTO.getVehicleId(), conditionsDTO.getConditionType(),
                conditionsDTO.getMinValue(), conditionsDTO.getMaxValue(), conditionsDTO.getPhenomenon(),
                0.50, conditionsDTO.getUsageForbidden());
        assertThrows(ConditionNotFoundException.class, () -> conditionsService.update(1L, updateDTO));
        verify(conditionsRepository, times(1)).findById(1L);
        verify(conditionsRepository, never()).save(any(Conditions.class));
    }

    @Test
    void testGetConditionsById() {
        when(conditionsRepository.findById(1L)).thenReturn(Optional.of(conditions));
        when(conditionsMapper.toDTO(conditions)).thenReturn(conditionsDTO);

        ConditionsDTO result = conditionsService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getConditionsId());
        assertEquals(ConditionType.PHENOMENON, result.getConditionType());
        assertEquals("Light rain", result.getPhenomenon());
        assertEquals(false, result.getUsageForbidden());
        verify(conditionsRepository, times(1)).findById(1L);
    }

    @Test
    void testGetConditionsByIdNotFound() {
        when(conditionsRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ConditionNotFoundException.class, () -> conditionsService.getById(1L));
        verify(conditionsRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllConditions() {
        List<Conditions> conditionsList = List.of(conditions);
        when(conditionsRepository.findAll()).thenReturn(conditionsList);
        when(conditionsMapper.toDTO(conditions)).thenReturn(conditionsDTO);

        List<ConditionsDTO> result = conditionsService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(conditionsRepository, times(1)).findAll();
    }

    @Test
    void testDeleteConditions() {
        when(conditionsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(conditionsRepository).deleteById(1L);

        assertDoesNotThrow(() -> conditionsService.delete(1L));
        verify(conditionsRepository, times(1)).existsById(1L);
        verify(conditionsRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteConditionsNotFound() {
        when(conditionsRepository.existsById(1L)).thenReturn(false);
        assertThrows(ConditionNotFoundException.class, () -> conditionsService.delete(1L));
        verify(conditionsRepository, times(1)).existsById(1L);
        verify(conditionsRepository, never()).deleteById(30L);
    }
}
