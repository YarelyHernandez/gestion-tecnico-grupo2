package com.gestion.services;

import com.gestion.data.Tecnico2;
import com.gestion.data.Tecnico2Repository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class Tecnico2Service {

    private final Tecnico2Repository repository;

    public Tecnico2Service(Tecnico2Repository repository) {
        this.repository = repository;
    }

    public Optional<Tecnico2> get(Long id) {
        return repository.findById(id);
    }

    public Tecnico2 update(Tecnico2 entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Tecnico2> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Tecnico2> list(Pageable pageable, Specification<Tecnico2> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
