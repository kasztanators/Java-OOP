package pl.edu.pg.eti.ksg.op.javka;

import java.util.Random;

public abstract class Plant extends Organism{
    public Plant(OrganismType organismType, World world, Point position, int age, int strength, int initiative) {
        super(organismType, world, position, age, strength, initiative);
        setSowingChance(0.3);
    }
    @Override
    public void Action(){
        Random rand = new Random();
        int upperbound = 100;
        int tmpRandomization = rand.nextInt(upperbound);
        if (tmpRandomization < getSowingChance() * 100) Sowing();
    }
    @Override
    public boolean IsAnimal() {
        return false;
    }


    protected void Sowing() {
        Point temp1Point = this.RandomNotOccupiedField(getPosition());
        if (temp1Point.equals(getPosition())) return;
        else {
            Organism tempOrganism = SpawnOrganism.CreateOrganism(getOrganismType(),  temp1Point,this.getWorld());
            Comment.AddComment("OMG New plant " + tempOrganism.OrganismToString()+" again :(");
            getWorld().AddOrganism(tempOrganism);
        }
    }

    @Override
    public void Collision(Organism other) {
    }
}
