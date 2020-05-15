package me.escoffier.mutiny.fight;

import io.smallrye.mutiny.Uni;
import me.escoffier.mutiny.fight.model.*;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Random;

@ApplicationScoped
public class FightService {

    @Inject @RestClient SupesServiceClient supes;

    private Random random = new Random();

    public Uni<Fight> fight() {

        // Retrieve hero and villain
        Uni<Hero> hero = supes.getRandomHero()
                .ifNoItem().after(Duration.ofMillis(500)).fail()
                .onFailure().recoverWithItem(Hero.FALLBACK);

        Uni<Villain> villain = supes.getRandomVillain()
                .ifNoItem().after(Duration.ofMillis(500)).fail()
                .onFailure().recoverWithItem(Villain.FALLBACK);

        // Combine both and call computeFightOutcome
        return Uni.combine().all().unis(hero, villain)
                .combinedWith(this::computeFightOutcome);

    }

    //----------------------------------

    private Fight computeFightOutcome(Hero hero, Villain villain) {
        Fight fight = new Fight();
        fight.hero = hero;
        fight.villain = villain;
        int heroAdjust = random.nextInt(20);
        int villainAdjust = random.nextInt(20);

        if ((hero.level + heroAdjust)
                > (villain.level + villainAdjust)) {
            fight.winner = hero.name;
        } else if (hero.level < villain.level) {
            fight.winner = villain.name;
        } else {
            fight.winner = random.nextBoolean() ?
                    hero.name : villain.name;
        }
        sendToKafka(fight);
        return fight;
    }

    //----------------------------------

    @Inject @Channel("fights") Emitter<Fight> emitter;

    private void sendToKafka(Fight fight) {
        // send fight.
        emitter.send(fight);
    }


}
