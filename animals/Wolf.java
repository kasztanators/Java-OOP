package pl.edu.pg.eti.ksg.op.javka.animals;
import pl.edu.pg.eti.ksg.op.javka.Animal;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;

public class Wolf extends Animal{
    private static final int MOV_RAN = 1;
    private static final double MOV_PTS = 1;
    private static final int STRENGTH = 9;
    private static final int INITIATIVE = 5;
    public Wolf( World world, Point position, int age) {
        super(OrganismType.WOLF, world, position, age, STRENGTH, INITIATIVE);
        this.setMovementRange(MOV_RAN);
        this.setMovementPoints(MOV_PTS);
        setImage("wolf.jpg");
    }
    @Override
    public String OrganismTypeToString() {
        return "Wolf";
    }
}
