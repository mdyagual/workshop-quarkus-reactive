package co.com.api.compras.service;

import co.com.api.compras.dto.SolicitudDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface SolicitudService {
    Multi<SolicitudDTO> listAll();

    Uni<SolicitudDTO> listById(String id);

    Multi<SolicitudDTO> listByPropietario(String propietario);

    Uni<SolicitudDTO> save(SolicitudDTO solicitudDTO);

    Uni<SolicitudDTO> update(SolicitudDTO solicitudDTO);

    Uni<Long> delete(String id);

}
