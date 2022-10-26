package pl.edu.pg.eti.ksg.op.javka.animals;
import pl.edu.pg.eti.ksg.op.javka.Animal;
import pl.edu.pg.eti.ksg.op.javka.Organism;
import pl.edu.pg.eti.ksg.op.javka.Comment;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;
import java.util.Random;

public class Antelope extends Animal{
    private static final int MOV_RAN = 2;
    private static final double MOV_PTS = 1;
    private static final int STRENGTH = 4;
    private static final int INITIATIVE = 4;

    public Antelope(World world, Point position, int age) {
        super(OrganismType.ANTELOPE, world, position, age, STRENGTH, INITIATIVE);
        this.setMovementPoints(MOV_PTS);
        this.setMovementRange(MOV_RAN);
        setImage("antelope.jpg");
    }
    @Override
    public String OrganismTypeToString() {
        return "Antelope";
    }

    @Override
    public boolean SpecialAttackEngine(Organism attacker, Organism enemy) {
        Random rand = new Random();
        int tempRandomize = rand.nextInt(100);
        if (tempRandomize < 50) {
            if (this == attacker) {
                Comment.AddComment(OrganismToString() + " runs from " + enemy.OrganismToString());
                Point tmpPosition = RandomNotOccupiedField(enemy.getPosition());
                if (!tmpPosition.equals(enemy.getPosition()))
                    MakingMove(tmpPosition);
            } else if (this == enemy) {
                Comment.AddComment(OrganismToString() + "  runs " + attacker.OrganismToString());
                Point tmpPosition = this.getPosition();
                MakingMove(RandomNotOccupiedField(this.getPosition()));
                if (getPosition().equals(tmpPosition)) {
                    getWorld().DeleteOrganism(this);
                    Comment.AddComment(attacker.OrganismToString() + " kills " + OrganismToString());
                }
                attacker.MakingMove(tmpPosition);
            }
            return true;
        } else return false;
    }
}
