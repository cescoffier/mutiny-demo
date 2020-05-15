package me.escoffier.mutiny.stats;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import me.escoffier.mutiny.stats.model.Stats;
import me.escoffier.mutiny.stats.model.Fight;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FightProcessor {

    @Incoming("fights")
    @Outgoing("stats")
    @Broadcast
    public Multi<Stats> process(Multi<Fight> fights) {
       return fights
               .onItem().scan(
                       Stats::new,
                       this::compute
       );
    }

    private Stats compute(Stats s, Fight f) {
        if (f.winner.equals(f.hero.name)) {
            s.wonByHeroes = s.wonByHeroes + 1;
        } else {
            s.wonByVillains = s.wonByVillains + 1;
        }
        return s;
    }

}
