package com.foodDelivery.api.controller.impl;

import com.foodDelivery.api.controller.CRUDController;
import com.foodDelivery.api.dto.ConditionsDTO;
import com.foodDelivery.api.service.ConditionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/conditions")
public class ConditionsController implements CRUDController<ConditionsDTO, Long> {

    private final ConditionsService conditionsService;

    @Override
    public ResponseEntity<ConditionsDTO> create(ConditionsDTO dto) {
        ConditionsDTO conditionDTO = conditionsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(conditionDTO);
    }

    @Override
    public ResponseEntity<ConditionsDTO> update(Long id, ConditionsDTO dto) {
        ConditionsDTO conditionsDTO = conditionsService.update(id, dto);
        return ResponseEntity.ok(conditionsDTO);
    }

    @Override
    public ResponseEntity<ConditionsDTO> getById(Long id) {
        ConditionsDTO conditionsDTO = conditionsService.getById(id);
        return ResponseEntity.ok(conditionsDTO);
    }

    @Override
    public ResponseEntity<List<ConditionsDTO>> getAll() {
        List<ConditionsDTO> conditionsDTOS = conditionsService.getAll();
        return ResponseEntity.ok(conditionsDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return null;
    }
}
