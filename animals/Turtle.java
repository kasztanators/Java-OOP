package pl.edu.pg.eti.ksg.op.javka.animals;

import pl.edu.pg.eti.ksg.op.javka.Animal;
import pl.edu.pg.eti.ksg.op.javka.Organism;
import pl.edu.pg.eti.ksg.op.javka.Comment;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;

public class Turtle extends Animal {
    private static final int MOV_RAN = 1;
    private static final double MOV_PTS = 0.25;
    private static final int STRENGTH = 2;
    private static final int INITIATIVE = 1;

    public Turtle(World world, Point position, int age) {
        super(OrganismType.TURTLE, world, position, age, STRENGTH, INITIATIVE);
        this.setMovementRange(MOV_RAN);
        this.setMovementPoints(MOV_PTS);
        setImage("turtle.jpg");
    }

    @Override
    public String OrganismTypeToString() {
        return "Turtle";
    }

    @Override
    public boolean SpecialAttackEngine(Organism attacker, Organism enemy) {
        if (this == enemy) {
            if (attacker.getStrength() < 5 && attacker.IsAnimal()) {
                Comment.AddComment(OrganismToString() + " defends" + attacker.OrganismToString());
                return true;
            } else return false;
        } else {
            if (attacker.getStrength() >= enemy.getStrength()) return false;
            else {
                if (enemy.getStrength() < 5 && enemy.IsAnimal()) {
                    Comment.AddComment(OrganismToString() + " defends " + enemy.OrganismToString());
                    return true;
                } else return false;
            }
        }
    }
}
