package org.example.smartqueue.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.response.NotificationResponseDTO;
import org.example.smartqueue.mapper.NotificationMapper;
import org.example.smartqueue.repository.NotificationRepository;
import org.example.smartqueue.service.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

@RequiredArgsConstructor
public abstract class NotificationServiceImp implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender mailSender;
    @Async
    public void sendEmailAsync(String to, String subject, String body){

       try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setText(body);
            mailSender.send(email);
        }catch(Exception e){
           System.out.println("Erreur lors de l'envoi de l'email à"+to+e.getMessage());

       }

    }

//    @Override
//     public void notificationConfirmation(){
//
//    }
//    @Override
//    void sendTurnApproachingNotification(){
//
//    }
//    @Override
//    void notificationUrTurn(){
//
//    }
//    @Override
//    void notificationAnnulationTicket(long id){
//
//    }
//    @Override
//    List<NotificationResponseDTO> getNotificationsByClient(long idClient){
//
//    }






}
