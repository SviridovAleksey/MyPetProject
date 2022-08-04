package com.sviridov.resource.service.impl;

import java.util.Optional;

import com.sviridov.resource.persistence.model.Foo;
import com.sviridov.resource.persistence.repository.IFooRepository;
import com.sviridov.resource.service.IFooService;
import org.springframework.stereotype.Service;

@Service
public class FooServiceImpl implements IFooService {

    private IFooRepository fooRepository;

    public FooServiceImpl(IFooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    @Override
    public Optional<Foo> findById(Long id) {
        return fooRepository.findById(id);
    }

    @Override
    public Foo save(Foo foo) {
        return fooRepository.save(foo);
    }

    @Override
    public Iterable<Foo> findAll() {
        return fooRepository.findAll();
    }
}
