package com.foodDelivery.api.service.impl;

import com.foodDelivery.api.dto.BaseFeeDTO;
import com.foodDelivery.api.exception.BaseFeeNotFoundException;
import com.foodDelivery.api.mapper.BaseFeeMapper;
import com.foodDelivery.api.model.BaseFee;
import com.foodDelivery.api.repository.BaseFeeRepository;
import com.foodDelivery.api.service.BaseFeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the BaseFee service interface.
 *
 * <p>
 *     Provides basic CRUD operations.
 * </p>
 */
@Service
@AllArgsConstructor
public class BaseFeeServiceImpl implements BaseFeeService {

    private final BaseFeeRepository baseFeeRepository;
    private final BaseFeeMapper baseFeeMapper;

    @Override
    public BaseFeeDTO create(BaseFeeDTO dto) {
        BaseFee baseFee = baseFeeMapper.toEntity(dto);
        BaseFee saved = baseFeeRepository.save(baseFee);
        return baseFeeMapper.toDTO(saved);
    }

    @Override
    public BaseFeeDTO update(Long id, BaseFeeDTO dto) {
       BaseFee baseFee = baseFeeRepository.findById(id)
               .orElseThrow(() -> new BaseFeeNotFoundException("BaseFee with id " + id + " was not found."));

       baseFee.setBaseFeeId(dto.getBaseFeeId());

       BaseFee updated = baseFeeRepository.save(baseFee);
       return baseFeeMapper.toDTO(updated);
    }

    @Override
    public BaseFeeDTO getById(Long id) {
        BaseFee baseFee = baseFeeRepository.findById(id)
                .orElseThrow(() -> new BaseFeeNotFoundException("BaseFee with id " + id + " was not found."));
        return baseFeeMapper.toDTO(baseFee);
    }

    @Override
    public List<BaseFeeDTO> getAll() {
        return baseFeeRepository.findAll()
                .stream()
                .map(baseFeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!baseFeeRepository.existsById(id)) {
            throw new BaseFeeNotFoundException("BaseFee with id " + id + " was not found.");
        }
        baseFeeRepository.deleteById(id);
    }
}
