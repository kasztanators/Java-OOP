package pl.edu.pg.eti.ksg.op.javka.plants;

import pl.edu.pg.eti.ksg.op.javka.Plant;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;

public class SosnowskyHogweed extends Plant {
    private static final int STRENGTH = 10;
    private static final int INITIATIVE = 0;

    public SosnowskyHogweed(World world, Point position, int age) {
        super(OrganismType.SOSNOWSKY_HOGWEED, world, position,
                age, STRENGTH, INITIATIVE);
        setImage("barszcz.jpeg");
        setSowingChance(0.05);
    }
    @Override
    public String OrganismTypeToString() {
        return "Sosnowsky Hogweed";
    }


}
