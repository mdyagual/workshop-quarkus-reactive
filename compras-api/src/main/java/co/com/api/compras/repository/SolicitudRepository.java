package co.com.api.compras.repository;

import co.com.api.compras.entity.Solicitud;
import co.com.api.compras.utils.Usuario;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SolicitudRepository implements ReactivePanacheMongoRepositoryBase<Solicitud, String> {

    public Uni<List<Solicitud>> getAll(){
        return findAll().stream().collect().asList();
    }

    public Uni<Solicitud> searchById(String idSolicitud){
        return find("id", idSolicitud).firstResult();
    }

    public Multi<Solicitud> searchByUsuario(Usuario usuario){
       return findAll()
              .stream()
           .filter(solicitud -> solicitud
                      .getUsuario()
                  .equals(usuario));
    }

    public Uni<Solicitud> add(Solicitud solicitud){
        return persist(solicitud).call(solicitud1 -> searchById(solicitud1.getSolicitudId()));
    }

    public Uni<Solicitud> modify(Solicitud solicitud){
        return Uni.createFrom()
                .item(solicitud)
                .onItem()
                .transformToUni(solicitud1 -> {
                    System.out.println(solicitud1);
                    return remove(solicitud1.getSolicitudId());
                })
                        .onItem()
                        .transformToUni(aBoolean -> add(solicitud));
    }

    public Uni<Long> remove(String idSolicitud){
        return delete("id", idSolicitud);
    }


}
