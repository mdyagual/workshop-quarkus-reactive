package co.com.api.compras.repository;

import co.com.api.compras.entity.Solicitud;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class SolicitudRepository implements ReactivePanacheMongoRepositoryBase<Solicitud, String> {

    public Multi<Solicitud> getAll(){
        return streamAll();
    }

    public Uni<Solicitud> searchById(String idSolicitud){
        return find("solicitudId", idSolicitud).firstResult();
    }

    public Multi<Solicitud> searchByPropietario(String propietario){
       return streamAll()
           .filter(solicitud -> solicitud
                      .getUsuario().getTarjeta().getPropietario()
                  .equals(propietario));
    }

    public Uni<Solicitud> add(Solicitud solicitud){
        return persist(solicitud).call(solicitud1 -> searchById(solicitud1.getSolicitudId()));
    }

    public Uni<Solicitud> modify(Solicitud solicitud){
        return Uni.createFrom()
                .item(solicitud)
                .onItem()
                .transformToUni(solicitud1 -> remove(solicitud1.getSolicitudId()))
                        .onItem()
                        .transformToUni(aBoolean -> add(solicitud));

    }

    public Uni<Long> remove(String idSolicitud){
        return delete("solicitudId", idSolicitud);
    }


}
