package me.escoffier.mutiny.supes;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import me.escoffier.mutiny.supes.model.Hero;
import me.escoffier.mutiny.supes.model.Villain;
import org.jboss.resteasy.annotations.SseElementType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@Path("/supes")
@Produces(MediaType.APPLICATION_JSON)
public class SupesResource {

    @GET
    public String hello() {
        return "hello";
    }



    // Hero and Villain methods


    // Stream of heroes




}
