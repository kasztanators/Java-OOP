package pl.edu.pg.eti.ksg.op.javka.plants;

import pl.edu.pg.eti.ksg.op.javka.Plant;
import pl.edu.pg.eti.ksg.op.javka.Organism;
import pl.edu.pg.eti.ksg.op.javka.Comment;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;

import java.util.Random;


public class Belladona extends Plant {
    private static final int STRENGTH = 99;
    private static final int INITIATIVE = 0;

    public Belladona(World world, Point position, int age) {
        super(OrganismType.BELLADONNA, world, position, age, STRENGTH, INITIATIVE);
        setImage("wilcza.jpg");
        setSowingChance(0.05);
    }


    @Override
    public void Action() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpRandomizing = rand.nextInt(upperbound);
        if (tmpRandomizing < getSowingChance() * 100) Sowing();
    }

    @Override
    public String OrganismTypeToString() {
        return "Belladonna";
    }

    @Override
    public boolean SpecialAttackEngine(Organism attacker, Organism enemy) {
        Comment.AddComment(attacker.OrganismToString() + " eats " + this.OrganismToString());
        if (attacker.getStrength() >= 99) {
            getWorld().DeleteOrganism(this);
            Comment.AddComment(attacker.OrganismToString() + " destroyesss Belladonna ");
        }
        if (attacker.IsAnimal()) {
            getWorld().DeleteOrganism(attacker);
            Comment.AddComment(attacker.OrganismToString() + " was poisoned");
        }
        return true;
    }
}
