package me.escoffier.mutiny.supes.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;

import java.util.Random;

@MongoEntity(collection = "villains")
public class Villain extends ReactivePanacheMongoEntity {

    public String name;
    public int level;
    public String image;

    public static Uni<Villain> findRandom() {
        Random random = new Random();
        return Uni.createFrom().nullItem();
    }


}
