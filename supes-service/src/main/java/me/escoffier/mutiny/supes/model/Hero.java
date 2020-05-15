package me.escoffier.mutiny.supes.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;

import java.util.Random;

@MongoEntity(collection = "heroes")
public class Hero extends ReactivePanacheMongoEntity {

    public String name;
    public int level;
    public String image;

    public static Uni<Hero> findRandom() {
        Random random = new Random();
        return Hero.count()
                .map(l -> random.nextInt(l.intValue()))
                .flatMap(index -> {
                    return Hero.findAll().page(index, 1).firstResult();
                });
    }
}
