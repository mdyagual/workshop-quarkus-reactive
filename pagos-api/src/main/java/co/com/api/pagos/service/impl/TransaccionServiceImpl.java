package co.com.api.pagos.service.impl;

import co.com.api.pagos.config.TransaccionMapper;
import co.com.api.pagos.dto.TransaccionDTO;
import co.com.api.pagos.repository.TransaccionRepository;
import co.com.api.pagos.service.TransaccionService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository mongoDb;

    private final TransaccionMapper mapper;

    @Override
    public Multi<TransaccionDTO> listAll() {
        return mongoDb.getAll().map(mapper::toDto);
    }

    @Override
    public Uni<TransaccionDTO> listById(String id) {
        return mongoDb.searchById(id).map(mapper::toDto);
    }

    @Override
    public Uni<TransaccionDTO> save(TransaccionDTO transaccionDTO) {
        return mongoDb.add(mapper.toEntity(transaccionDTO)).map(mapper::toDto);
    }

    @Override
    public Uni<TransaccionDTO> update(TransaccionDTO transaccionDTO) {
        return mongoDb.modify(mapper.toEntity(transaccionDTO)).map(mapper::toDto);
    }

    @Override
    public Uni<Long> delete(String id) {
        return mongoDb.remove(id);
    }
}
