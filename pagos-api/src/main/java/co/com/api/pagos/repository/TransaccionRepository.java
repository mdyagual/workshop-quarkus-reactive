package co.com.api.pagos.repository;

import co.com.api.pagos.entity.Transaccion;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class TransaccionRepository implements ReactivePanacheMongoRepositoryBase<Transaccion, String> {
    public Multi<Transaccion> getAll(){
        return streamAll();
    }

    public Uni<Transaccion> searchById(String idTransaccion){
        return find("transaccionId", idTransaccion).firstResult();
    }

    public Uni<Transaccion> add(Transaccion transaccion){
        return persist(transaccion).call(transaccion1 -> searchById(transaccion1.getTransaccionId()));
    }

    public Uni<Transaccion> modify(Transaccion transaccion){
        return Uni.createFrom()
                .item(transaccion)
                .onItem()
                .transformToUni(transaccion1 -> remove(transaccion1.getTransaccionId()))
                .onItem()
                .transformToUni(aLong -> add(transaccion));
    }

    public Uni<Long> remove(String idTransaccion){
        return delete("transaccionId", idTransaccion);
    }
}
