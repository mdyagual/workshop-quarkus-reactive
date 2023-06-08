package co.com.api.compras.service;

import co.com.api.compras.dto.SolicitudDTO;
import co.com.api.compras.utils.Usuario;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface SolicitudService {
    Uni<List<SolicitudDTO>> listAll();

    Uni<SolicitudDTO> listById(String id);

    Multi<SolicitudDTO> listByUsuario(Usuario usuario);

    Uni<SolicitudDTO> save(SolicitudDTO solicitudDTO);

    Uni<SolicitudDTO> update(SolicitudDTO solicitudDTO);

    Uni<Long> delete(String id);

}
