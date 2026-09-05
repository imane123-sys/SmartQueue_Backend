package org.example.smartqueue.mapper;

import org.example.smartqueue.dto.request.ServiceRequestDTO;
import org.example.smartqueue.dto.response.ServiceResponseDTO;
import org.example.smartqueue.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mapping(source = "etablissement.id", target = "etablissementId")
    @Mapping(source = "etablissement.nom", target = "nomEtablissement")
    ServiceResponseDTO toResponseDTO(Service service);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etablissement", ignore = true)
    Service toEntity(ServiceRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etablissement", ignore = true)
    void updateEntityFromDto(ServiceRequestDTO requestDTO, @MappingTarget Service service);
}
