package com.gestion.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Tecnico2Repository extends JpaRepository<Tecnico2, Long>, JpaSpecificationExecutor<Tecnico2> {

}
