package pl.edu.pg.eti.ksg.op.javka.animals;

import pl.edu.pg.eti.ksg.op.javka.Animal;
import pl.edu.pg.eti.ksg.op.javka.Organism;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;

import java.util.Random;

public class Fox extends Animal {
    private static final int MOV_RAN = 1;
    private static final double MOV_PTS = 1;
    private static final int STRENGTH = 3;
    private static final int INITIATIVE = 7;
    public Fox( World world, Point position, int age) {
        super(OrganismType.WOLF, world, position, age, STRENGTH, INITIATIVE);
        this.setMovementRange(MOV_RAN);
        this.setMovementPoints(MOV_PTS);
        setImage("fox.jpg");
    }

    @Override
    public String OrganismTypeToString() {
        return "Fox";
    }

    @Override
    public Point RandomField(Point position) {
        UnlockAllDirections();
        int posX = position.getX();
        int posY = position.getY();
        int sizeX = getWorld().getSizeX();
        int sizeY = getWorld().getSizeY();
        int DirectionCount = 0;
        Organism tmpOrganism;

        if (posX == 0) LockDirection(Direction.ELEFT);
        else {
            tmpOrganism = getWorld().getBoard()[posY][posX - 1];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) {
                LockDirection(Direction.ELEFT);
            } else DirectionCount++;
        }

        if (posX == sizeX - 1) LockDirection(Direction.ERIGHT);
        else {
            tmpOrganism = getWorld().getBoard()[posY][posX + 1];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) {
                LockDirection(Direction.ERIGHT);
            } else DirectionCount++;
        }

        if (posY == 0) LockDirection(Direction.EUP);
        else {
            tmpOrganism = getWorld().getBoard()[posY - 1][posX];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) {
                LockDirection(Direction.EUP);
            } else DirectionCount++;
        }

        if (posY == sizeY - 1) LockDirection(Direction.EDOWN);
        else {
            tmpOrganism = getWorld().getBoard()[posY + 1][posX];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) {
                LockDirection(Direction.EDOWN);
            } else DirectionCount++;
        }

        if (DirectionCount == 0) return new Point(posX, posY);
        while (true) {
            Random rand = new Random();
            int upperbound = 100;
            int tempRandomizing = rand.nextInt(upperbound);
            //RUCH W LEWO
            if (tempRandomizing < 25 && !IsDirectionLocked(Direction.ELEFT)){return new Point(posX - 1, posY);}
                //RUCH W PRAWO
            else if (tempRandomizing >= 25 && tempRandomizing < 50 && !IsDirectionLocked(Direction.ERIGHT)){return new Point(posX + 1, posY);}
                //RUCH W GORE
            else if (tempRandomizing >= 50 && tempRandomizing < 75 && !IsDirectionLocked(Direction.EUP)){return new Point(posX, posY - 1);}
                //RUCH W DOL
            else if (tempRandomizing >= 75 && !IsDirectionLocked(Direction.EDOWN)){return new Point(posX, posY + 1);}

        }
    }
}