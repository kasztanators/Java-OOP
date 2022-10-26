package pl.edu.pg.eti.ksg.op.javka.plants;

import pl.edu.pg.eti.ksg.op.javka.Plant;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;



public class Grass extends Plant {
    private static final int STRENGTH = 0;
    private static final int INITIATIVE = 0;
    public Grass(World world, Point position, int age) {
        super(OrganismType.GRASS, world, position, age, STRENGTH, INITIATIVE);
        setImage("grass.jpg");
    }
    @Override
    public String OrganismTypeToString(){
        return "Grasss";
    }
}
