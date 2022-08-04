package com.sviridov.resource.service;

import java.util.Optional;

import com.sviridov.resource.persistence.model.Foo;

public interface IFooService {
    Optional<Foo> findById(Long id);

    Foo save(Foo foo);

    Iterable<Foo> findAll();

}
