package co.com.api.pagos.config;

import co.com.api.pagos.dto.TransaccionDTO;
import co.com.api.pagos.entity.Transaccion;
import org.mapstruct.*;

@Mapper(componentModel = "jakarta", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransaccionMapper {

    @Mapping(target = "transaccionId", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    Transaccion toEntity(TransaccionDTO transaccionDTO);

    @AfterMapping
    default void mapId(TransaccionDTO transaccionDTO, @MappingTarget Transaccion transaccion) {
        if (transaccionDTO.getTransaccionId() != null) {
            transaccion.setTransaccionId(transaccionDTO.getTransaccionId());
        }
    }
    @Mapping(target = "transaccionId", expression = "java(transaccion.getTransaccionId())")
    @Mapping(target = "timestamp", expression = "java(transaccion.getTimestamp())")
    TransaccionDTO toDto (Transaccion transaccion);
}
