package org.example.smartqueue.repository;

import org.example.smartqueue.entity.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {

    List<Etablissement> findByNomContainingIgnoreCaseOrAdresseContainingIgnoreCase(String nom, String adresse);

    @Query("SELECT e FROM Etablissement e WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(e.latitude)) * " +
            "cos(radians(e.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(e.latitude)))) <= :rayonKm")
    List<Etablissement> findEtablissementsProches(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("rayonKm") double rayonKm
    );
}
