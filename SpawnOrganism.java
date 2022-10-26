package pl.edu.pg.eti.ksg.op.javka;

import pl.edu.pg.eti.ksg.op.javka.animals.*;
import pl.edu.pg.eti.ksg.op.javka.plants.*;
public class SpawnOrganism {
    public static Organism CreateOrganism(Organism.OrganismType organismType, Point position, World world) {
        if (organismType == Organism.OrganismType.WOLF) {
            return new Wolf(world, position, world.getTurnCount());
        } else if (organismType == Organism.OrganismType.SHEEP) {
            return new Sheep(world, position, world.getTurnCount());
        } else if (organismType == Organism.OrganismType.FOX) {
            return new Fox(world, position, world.getTurnCount());
        } else if (organismType == Organism.OrganismType.TURTLE) {
            return new Turtle(world, position, world.getTurnCount());
        } else if (organismType == Organism.OrganismType.ANTELOPE) {
            return new Antelope(world, position, world.getTurnCount());
        } else if (organismType == Organism.OrganismType.HUMAN) {
            return new Human(world, position, world.getTurnCount());
        } else if (organismType == Organism.OrganismType.GRASS) {
            return new Grass(world, position, world.getTurnCount());
        } else if (organismType == Organism.OrganismType.SOW_THISTLE) {
            return new SowThistle(world, position, world.getTurnCount());
        } else if (organismType == Organism.OrganismType.GUARANA) {
            return new Guarana(world, position, world.getTurnCount());
        } else if (organismType == Organism.OrganismType.BELLADONNA) {
            return new Belladona(world, position, world.getTurnCount());
        }else if (organismType == Organism.OrganismType.SOSNOWSKY_HOGWEED) {
            return new SosnowskyHogweed(world, position, world.getTurnCount());
        }
        else return null;
    }
}
