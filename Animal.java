package pl.edu.pg.eti.ksg.op.javka;

import java.util.Random;

public abstract class Animal extends Organism {
    private int movementRange;
    private double movementPoints;

    public Animal(OrganismType organismType, World world, Point position, int age, int strength, int initiative) {
        super(organismType, world, position, age, strength, initiative);
        setIsSowing(false);
        setSowingChance(0.5);
    }

    @Override
    public void Action() {
        for (int i = 0; i < movementRange; i++) {
            Point nextPosition = ScheduleMove();

            if (getWorld().IsFieldOccupied(nextPosition)
                    && getWorld().CheckField(nextPosition) != this) {
                Collision(getWorld().CheckField(nextPosition));
                break;
            } else if (getWorld().CheckField(nextPosition) != this) MakingMove(nextPosition);
        }
    }

    @Override
    public void Collision(Organism other) {
        if (getOrganismType() == other.getOrganismType()) {
            Random rand = new Random();
            int tmpRandomize = rand.nextInt(100);
            if (tmpRandomize < getSowingChance() * 100) Sowing(other);
        } else {
            if (other.SpecialAttackEngine(this, other)) return;
            if (SpecialAttackEngine(this, other)) return;

            if (getStrength() >= other.getStrength()) {
                getWorld().DeleteOrganism(other);
                MakingMove(other.getPosition());
                Comment.AddComment(OrganismToString() + " slays " + other.OrganismToString());
            } else {
                getWorld().DeleteOrganism(this);
                Comment.AddComment(other.OrganismToString() + " slays " + OrganismToString());
            }
        }
    }

    @Override
    public boolean IsAnimal() {
        return true;
    }

    protected Point ScheduleMove() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpRandomize = rand.nextInt(upperbound);
        if (tmpRandomize >= (int) (movementPoints * 100)) return getPosition();
        else return RandomField(getPosition());
    }

    private void Sowing(Organism other) {
        if (this.getIsSowing() || other.getIsSowing()) return;
        Point tmp1Point = this.RandomNotOccupiedField(getPosition());
        if (tmp1Point.equals(getPosition())) {
            Point tmp2Point = other.RandomNotOccupiedField(other.getPosition());
            if (tmp2Point.equals(other.getPosition())) return;
            else {
                Organism tmpOrganism = SpawnOrganism.CreateOrganism(getOrganismType(),  tmp2Point,this.getWorld());
                Comment.AddComment("Was born " + tmpOrganism.OrganismToString());
                getWorld().AddOrganism(tmpOrganism);
                setIsSowing(true);
                other.setIsSowing(true);
            }
        } else {
            Organism tmpOrganism = SpawnOrganism.CreateOrganism(getOrganismType(),  tmp1Point,this.getWorld());
            Comment.AddComment("Says Hi to the planet: " + tmpOrganism.OrganismToString());
            getWorld().AddOrganism(tmpOrganism);
            setIsSowing(true);
            other.setIsSowing(true);
        }
    }

    public int getMovementRange() {
        return movementRange;
    }

    public void setMovementRange(int movementRange) {
        this.movementRange = movementRange;
    }

    public double getMovementPoints() {
        return movementPoints;
    }

    public void setMovementPoints(double movementPoints) {
        this.movementPoints = movementPoints;
    }
}
