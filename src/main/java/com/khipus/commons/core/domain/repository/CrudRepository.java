package com.khipus.commons.core.domain.repository;

import java.util.Collection;

import graphql.com.google.common.base.Optional;

public interface CrudRepository<T, ID>{
    Collection<T> findAll();

    Optional<T> findById(ID id);

    T save(T aggregate);
    
    void softDelete(T aggregate);

    void hardDelete(ID id);

    boolean existsByField(UniqueField field, String value);

    boolean existsById(ID id);
}
