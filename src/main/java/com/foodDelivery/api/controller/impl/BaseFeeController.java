package com.foodDelivery.api.controller.impl;

import com.foodDelivery.api.controller.CRUDController;
import com.foodDelivery.api.dto.BaseFeeDTO;
import com.foodDelivery.api.service.BaseFeeService;
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
@RequestMapping("/api/base-fee")
public class BaseFeeController implements CRUDController<BaseFeeDTO, Long> {

    private final BaseFeeService baseFeeService;

    @Override
    public ResponseEntity<BaseFeeDTO> create(BaseFeeDTO dto) {
        BaseFeeDTO baseFeeDTO = baseFeeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(baseFeeDTO);
    }

    @Override
    public ResponseEntity<BaseFeeDTO> update(Long id, BaseFeeDTO dto) {
        BaseFeeDTO baseFeeDTO = baseFeeService.update(id, dto);
        return ResponseEntity.ok(baseFeeDTO);
    }

    @Override
    public ResponseEntity<BaseFeeDTO> getById(Long id) {
        BaseFeeDTO baseFeeDTO = baseFeeService.getById(id);
        return ResponseEntity.ok(baseFeeDTO);
    }

    @Override
    public ResponseEntity<List<BaseFeeDTO>> getAll() {
        List<BaseFeeDTO> baseFeeDTOS = baseFeeService.getAll();
        return ResponseEntity.ok(baseFeeDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        baseFeeService.delete(id);
        return ResponseEntity.ok("Base fee with id " + id + " was deleted!");
    }
}
