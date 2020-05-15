package me.escoffier.mutiny.fight.model;

public class Hero {

    public String name;
    public int level;
    public String image;

    public static final Hero FALLBACK;

    static {
        FALLBACK = new Hero();
        FALLBACK.name = "Donatello (fallback)";
        FALLBACK.image = "https://www.superherodb.com/pictures2/portraits/10/050/10330.jpg";
        FALLBACK.level = 15;
    }


}
