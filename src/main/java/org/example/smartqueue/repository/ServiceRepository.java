package org.example.smartqueue.repository;

import org.example.smartqueue.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {

    List<Services> findByEtablissementId(Long etablissementId);

    boolean existsByNomAndEtablissementId(String nom, Long etablissementId);
//    List<Etablissement>findByS
}