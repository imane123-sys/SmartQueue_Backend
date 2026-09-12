package org.example.smartqueue.repository;

import org.example.smartqueue.dto.response.EtablissementResponseDTO;
import org.example.smartqueue.entity.Etablissement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {

    @Query("""
    SELECT e FROM Etablissement e
    JOIN e.services s
    WHERE LOWER(s.nom) = LOWER(:serviceNom)
    AND (
        6371 * acos(
            cos(radians(:latitude)) * cos(radians(e.latitude)) *
            cos(radians(e.longitude) - radians(:longitude)) +
            sin(radians(:latitude)) * sin(radians(e.latitude))
        )
    ) <= :rayonKm
    ORDER BY (
        6371 * acos(
            cos(radians(:latitude)) * cos(radians(e.latitude)) *
            cos(radians(e.longitude) - radians(:longitude)) +
            sin(radians(:latitude)) * sin(radians(e.latitude))
        )
    )
    """)
    List<Etablissement> findEtablissementsProchesParServices(
            @Param("serviceNom") String serviceNom,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("rayonKm") double rayonKm
    );
    Page<Etablissement>findAll(Pageable pageable);
}
