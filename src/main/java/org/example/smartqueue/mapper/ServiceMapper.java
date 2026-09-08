package org.example.smartqueue.mapper;

import org.example.smartqueue.dto.request.ServiceRequestDTO;
import org.example.smartqueue.dto.response.ServiceResponseDTO;
import org.example.smartqueue.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mapping(source = "etablissement.id", target = "etablissementId")
    @Mapping(source = "etablissement.nom", target = "nomEtablissement")
    ServiceResponseDTO toResponseDTO(Services services);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etablissement", ignore = true)
    Services toEntity(ServiceRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etablissement", ignore = true)
    void updateEntityFromDto(ServiceRequestDTO requestDTO, @MappingTarget Services services);
    List<ServiceResponseDTO>toDTOList(List<Services>services);
    void updateEntityFromDTO(ServiceRequestDTO service, @MappingTarget Services services)

}
