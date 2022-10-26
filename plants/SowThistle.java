package pl.edu.pg.eti.ksg.op.javka.plants;


import pl.edu.pg.eti.ksg.op.javka.Plant;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;

import java.util.Random;
import java.awt.*;

public class SowThistle extends Plant {
    private static final int STRENGTH = 0;
    private static final int INITIATIVE = 0;
    private static final int SOWING_ATTEMPT = 3;

    public SowThistle(World world, Point position, int age) {
        super(OrganismType.SOW_THISTLE, world, position,
                age, STRENGTH, INITIATIVE);
        setImage("mlecz.jpg");
    }

    @Override
    public void Action() {
        Random rand = new Random();
        for (int i = 0; i < SOWING_ATTEMPT; i++) {
            int tempRandomizing = rand.nextInt(100);
            if (tempRandomizing < getSowingChance()) Sowing();
        }
    }

    @Override
    public String OrganismTypeToString() {
        return "Sow Thistle";
    }
}