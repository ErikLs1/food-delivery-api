package com.foodDelivery.api.service.impl;

import com.foodDelivery.api.dto.ConditionsDTO;
import com.foodDelivery.api.exception.ConditionNotFoundException;
import com.foodDelivery.api.mapper.ConditionsMapper;
import com.foodDelivery.api.model.Conditions;
import com.foodDelivery.api.repository.ConditionsRepository;
import com.foodDelivery.api.service.ConditionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConditionsServiceImpl implements ConditionsService {

    private final ConditionsRepository conditionsRepository;
    private final ConditionsMapper conditionsMapper;


    @Override
    public ConditionsDTO create(ConditionsDTO dto) {
        Conditions conditions = conditionsMapper.toEntity(dto);
        Conditions saved = conditionsRepository.save(conditions);
        return conditionsMapper.toDTO(saved);
    }

    @Override
    public ConditionsDTO update(Long id, ConditionsDTO dto) {
        Conditions condition = conditionsRepository.findById(id)
                .orElseThrow(() -> new ConditionNotFoundException("Condition with id " + id + " was not found."));

        condition.setConditionFee(dto.getConditionFee());
        condition.setMinValue(dto.getMinValue());
        condition.setMaxValue(dto.getMaxValue());
        condition.setPhenomenon(dto.getPhenomenon());
        condition.setUsageForbidden(dto.getUsageForbidden());

        Conditions updated = conditionsRepository.save(condition);
        return conditionsMapper.toDTO(updated);
    }

    @Override
    public ConditionsDTO getById(Long id) {
        Conditions condition = conditionsRepository.findById(id)
                .orElseThrow(() -> new ConditionNotFoundException("Condition with id " + id + " was not found."));
        return conditionsMapper.toDTO(condition);
    }

    @Override
    public List<ConditionsDTO> getAll() {
        return conditionsRepository.findAll()
                .stream()
                .map(conditionsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!conditionsRepository.existsById(id)) {
            throw new ConditionNotFoundException("Condition with id " + id + " was not found.");
        }
        conditionsRepository.deleteById(id);
    }
}
