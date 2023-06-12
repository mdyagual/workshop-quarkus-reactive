package co.com.api.pagos.service;

import co.com.api.pagos.dto.TransaccionDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface TransaccionService {
    Multi<TransaccionDTO> listAll();

    Uni<TransaccionDTO> listById(String id);

    Uni<TransaccionDTO> save(TransaccionDTO transaccionDTO);

    Uni<TransaccionDTO> update(TransaccionDTO transaccionDTO);

    Uni<Long> delete(String id);
}
