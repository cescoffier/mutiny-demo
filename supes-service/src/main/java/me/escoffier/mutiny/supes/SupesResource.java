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

    @GET
    @Path("/greeting")
    public Uni<String> greeting() {
        return Uni.createFrom().item("greeting");
    }

    // Hero and Villain methods
    @GET
    @Path("/hero")
    public Uni<Hero> hero() {
        return Hero.findRandom();
    }

    @GET
    @Path("/villain")
    public Uni<Villain> villain() {
        return Villain.findRandom();
    }

    // Stream of heroes
    @GET
    @Path("/heroes")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<Hero> stream() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem().produceUni(x -> hero()).merge();
    }





}
