package org.example.smartqueue.repository;

import org.example.smartqueue.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByEtablissementId(Long etablissementId);

    boolean existsByNomAndEtablissementId(String nom, Long etablissementId);
}