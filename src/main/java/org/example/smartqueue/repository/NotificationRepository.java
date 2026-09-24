package org.example.smartqueue.repository;

import org.example.smartqueue.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.ticket.client.id = :clientId AND n.destinataireRole = org.example.smartqueue.enums.Role.CLIENT ORDER BY n.dateEnvoi DESC")
    Page<Notification> findByTicketClientId(@Param("clientId") Long clientId, Pageable pageable);

    @Query("SELECT n FROM Notification n WHERE n.ticket.services.etablissement.id = :etablissementId AND n.destinataireRole = org.example.smartqueue.enums.Role.ETABLISSEMENT ORDER BY n.dateEnvoi DESC")
    Page<Notification> findByTicket_Services_Etablissement_Id(@Param("etablissementId") Long etablissementId, Pageable pageable);
}