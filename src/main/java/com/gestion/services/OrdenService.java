package com.gestion.services;

import com.gestion.data.Orden;
import com.gestion.data.OrdenRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OrdenService {

    private final OrdenRepository repository;

    public OrdenService(OrdenRepository repository) {
        this.repository = repository;
    }

    public Optional<Orden> get(Long id) {
        return repository.findById(id);
    }

    public Orden update(Orden entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Orden> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Orden> list(Pageable pageable, Specification<Orden> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
