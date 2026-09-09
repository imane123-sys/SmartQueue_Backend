package org.example.smartqueue.repository;


import org.example.smartqueue.dto.response.TicketResponseDTO;
import org.example.smartqueue.entity.Ticket;
import org.example.smartqueue.enums.StatutTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByClientIdOrderByDateCreationDesc(Long clientId);

    List<Ticket> findByServicesIdAndStatutOrderByPositionAsc(Long serviceId, StatutTicket statut);

    long countByServicesIdAndStatutAndPositionLessThan(Long serviceId, StatutTicket statut, Integer position);

    @Query("SELECT MAX(t.position) FROM Ticket t WHERE t.services.id = :serviceId AND t.dateCreation >= :debutJournee")
    Optional<Integer> findMaxPositionDuJour(
            @Param("serviceId") Long serviceId,
            @Param("debutJournee") LocalDateTime debutJournee
    );

    boolean existsByClientIdAndServicesIdAndStatutIn(Long clientId, Long serviceId, List<StatutTicket> statutsActifs);
    List<Ticket>findByStatutAndServices_Nom(StatutTicket statut ,String nom);
}
