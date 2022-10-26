package pl.edu.pg.eti.ksg.op.javka.animals;

import pl.edu.pg.eti.ksg.op.javka.Animal;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;


public class Sheep extends Animal{
    private static final int MOV_RAN = 1;
    private static final double MOV_PTS = 1;
    private static final int STRENGTH = 4;
    private static final int INITIATIVE = 4;
    public Sheep( World world, Point position, int age) {
        super(OrganismType.SHEEP, world, position, age, STRENGTH, INITIATIVE);
        this.setMovementRange(MOV_RAN);
        this.setMovementPoints(MOV_PTS);
        setImage("sheep.jpg");
    }
    @Override
    public String OrganismTypeToString() {
        return "Sheep";
    }
}
