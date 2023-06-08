package co.com.api.compras.config;

import co.com.api.compras.dto.SolicitudDTO;
import co.com.api.compras.entity.Solicitud;
import org.mapstruct.*;

@Mapper(componentModel = "jakarta", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SolicitudMapper {
    @Mapping(target = "solicitudId", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    Solicitud toEntity(SolicitudDTO solicitudDTO);



    @AfterMapping
    default void mapId(SolicitudDTO solicitudDTO, @MappingTarget Solicitud solicitud) {
        if (solicitudDTO.getSolicitudId() != null) {
            solicitud.setSolicitudId(solicitudDTO.getSolicitudId());
        }
    }

    @Mapping(target = "solicitudId", expression  = "java(solicitud.getSolicitudId())")
    @Mapping(target = "timestamp", expression = "java(solicitud.getTimestamp())")
    SolicitudDTO toDto (Solicitud solicitud);


}
