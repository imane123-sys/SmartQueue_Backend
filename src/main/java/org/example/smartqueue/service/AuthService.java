package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.ClientRequestDTO;
import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.request.LoginRequestDTO;
import org.example.smartqueue.dto.request.RegisterRequestDTO;
import org.example.smartqueue.dto.response.AuthResponseDTO;



public interface AuthService {


    AuthResponseDTO registerClient(ClientRequestDTO requestDTO);



    AuthResponseDTO createEtablissementUser(EtablissementRequestDTO requestDTO);
    AuthResponseDTO login(LoginRequestDTO requestDTO);



}