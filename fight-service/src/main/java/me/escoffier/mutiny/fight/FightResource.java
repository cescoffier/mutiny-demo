package me.escoffier.mutiny.fight;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import me.escoffier.mutiny.fight.model.Fight;
import org.jboss.resteasy.annotations.SseElementType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@Path("/fight")
public class FightResource {

    @Inject FightService fights;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Fight> fight() {
        return fights.fight();
    }


    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<Fight> stream() {
        Multi<Long> ticks = Multi.createFrom().ticks()
            .every(Duration.ofSeconds(1));
        return ticks
                    .onItem().transformToUniAndMerge(x -> fight());
    }
}
