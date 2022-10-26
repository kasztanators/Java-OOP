package pl.edu.pg.eti.ksg.op.javka.plants;
import pl.edu.pg.eti.ksg.op.javka.Plant;
import pl.edu.pg.eti.ksg.op.javka.Organism;
import pl.edu.pg.eti.ksg.op.javka.Comment;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;

public class Guarana extends Plant{
    private static final int INCREMENT = 3;
    private static final int STRENGTH = 0;
    private static final int INITIATIVE = 0;

    public Guarana(World world, Point position, int age) {
        super(OrganismType.GUARANA, world, position,
                age, STRENGTH, INITIATIVE);
        setImage("gurana.jpeg");
    }

    @Override
    public String OrganismTypeToString() {
        return "Guarana";
    }

    @Override
    public boolean SpecialAttackEngine(Organism attacker, Organism enemy) {
        Point tmpPosition = this.getPosition();
        getWorld().DeleteOrganism(this);
        attacker.MakingMove(tmpPosition);
        Comment.AddComment(attacker.OrganismToString() + " eats " + this.OrganismToString()
                + "  increases power by: " + Integer.toString(INCREMENT));
        attacker.setStrength(attacker.getStrength() + INCREMENT);
        return true;
    }
}
