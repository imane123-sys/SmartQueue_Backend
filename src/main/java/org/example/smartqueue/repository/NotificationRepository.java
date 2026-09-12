package org.example.smartqueue.repository;
import org.example.smartqueue.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification>  findByTicketClientId(Long clientId,Pageable pageable);
    Page<Notification> findByTicket_Services_Etablissement_Id(Long etablissementId , Pageable pageable);
}