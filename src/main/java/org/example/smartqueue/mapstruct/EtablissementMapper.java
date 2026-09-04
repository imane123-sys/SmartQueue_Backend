package org.example.smartqueue.mapstruct;

import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.response.EtablissementResponseDTO;
import org.example.smartqueue.entity.Etablissement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EtablissementMapper {

    @Mapping(target = "distanceKm", ignore = true)
    @Mapping(target = "tempsAttenteEstimeMinutes", ignore = true)
    @Mapping(target = "nombrePersonnesEnAttente", ignore = true)
    EtablissementResponseDTO toResponseDTO(Etablissement etablissement);

    @Mapping(target = "id", ignore = true)
    Etablissement toEntity(EtablissementRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(EtablissementRequestDTO requestDTO, @MappingTarget Etablissement etablissement);
}