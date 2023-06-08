package co.com.api.compras.service.impl;

import co.com.api.compras.config.SolicitudMapper;
import co.com.api.compras.dto.SolicitudDTO;
import co.com.api.compras.repository.SolicitudRepository;
import co.com.api.compras.service.SolicitudService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;


@ApplicationScoped
@RequiredArgsConstructor
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository mongoDb;
    private  final SolicitudMapper mapper;

    @Override
    public Multi<SolicitudDTO> listAll() {
        return mongoDb.getAll().map(mapper::toDto);
    }

    @Override
    public Uni<SolicitudDTO> listById(String id) {
        return mongoDb.searchById(id).map(mapper::toDto);
    }

    @Override
    public Multi<SolicitudDTO> listByPropietario(String propietario) {
        return mongoDb.searchByPropietario(propietario).map(mapper::toDto);
    }

    @Override
    public Uni<SolicitudDTO> save(SolicitudDTO solicitudDTO) {
        return mongoDb.add(mapper.toEntity(solicitudDTO)).map(mapper::toDto);
    }

    @Override
    public Uni<SolicitudDTO> update(SolicitudDTO solicitudDTO) {
        return mongoDb.modify(mapper.toEntity(solicitudDTO)).map(mapper::toDto);
    }

    @Override
    public Uni<Long> delete(String id) {
        return mongoDb.remove(id);
    }
}
