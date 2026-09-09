package org.example.smartqueue.mapper;

import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.response.EtablissementResponseDTO;
import org.example.smartqueue.entity.Etablissement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EtablissementMapper {

    @Mapping(target = "distanceKm", ignore = true)
    @Mapping(target = "tempsAttenteEstimeMinutes", ignore = true)
    @Mapping(target = "nombrePersonnesEnAttente", ignore = true)

    EtablissementResponseDTO toResponseDTO(Etablissement etablissement);

    @Mapping(target = "id", ignore = true)
    Etablissement toEntity(EtablissementRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDto(EtablissementRequestDTO requestDTO, @MappingTarget Etablissement etablissement);
    List<EtablissementResponseDTO> toDTOList(List<Etablissement>etablissement);
}