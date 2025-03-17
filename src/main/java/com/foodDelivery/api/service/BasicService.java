package com.foodDelivery.api.service;

import java.util.List;

/**
 * Generic service interface for basic CRUD operations.
 *
 * @param <T> The DTO that is used in service methods
 * @param <ID> The type of entity's identifier
 */
public interface BasicService <T, ID> {

    /**
     * Create new entity from the provided DTO.
     *
     * @param dto The DTO containing necessary data for creating the entity.
     * @return Created entity as a DTO.
     */
    T create(T dto);

    /**
     * Updates existing entity using the given ID.
     *
     * @param id The ID of the entity to update.
     * @param dto The DTO containing the updated data.
     * @return The updated entity as a DTO.
     */
    T update(ID id, T dto);

    /**
     * Finds entity by its ID.
     *
     * @param id The ID of the entity to find.
     * @return The entity as DTO.
     */
    T getById(ID id);

    /**
     * Retrieves a list of all entities.
     *
     * @return A list of all entities as DTOs.
     */
    List<T> getAll();

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to delete.
     */
    void delete(ID id);
}
