package com.foodDelivery.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Generic CRUD Controller interface for handling basic CRUD operations.
 *
 * @param <T> The DTO type.
 * @param <ID> The identifier.
 */
public interface CRUDController<T, ID> {
    /**
     * Creates a new entity.
     *
     * @param dto the DTO representing the new entity.
     * @return a Response Entity containing the created entity
     */
    @PostMapping
    ResponseEntity<T> create(@RequestBody T dto);

    /**
     * Updates an existing entity.
     *
     * @param id the unique identifier of the entity to update.
     * @param dto the DTO containing updated data.
     * @return a ResponseEntity containing the updated entity.
     */
    @PutMapping("/{id}")
    ResponseEntity<T> update(@PathVariable("id") ID id, @RequestBody T dto);

    /**
     * Retrieves entity by its id.
     *
     * @param id the unique identifier of the entity.
     * @return a ResponseEntity containing the entity.
     */
    @GetMapping("/{id}")
    ResponseEntity<T> getById(@PathVariable("id") ID id);

    /**
     * Retrieves all entities.
     *
     * @return a ResponseEntity containing a list of entities.
     */
    @GetMapping
    ResponseEntity<List<T>> getAll();

    /**
     * Deletes an entity by its id.
     *
     * @param id the unique identifier of the entity to delete.
     * @return a ResponseEntity with a confirmation message.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable("id") ID id);
}
