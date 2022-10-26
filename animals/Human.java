package pl.edu.pg.eti.ksg.op.javka.animals;

import pl.edu.pg.eti.ksg.op.javka.Animal;
import pl.edu.pg.eti.ksg.op.javka.Organism;
import pl.edu.pg.eti.ksg.op.javka.Comment;
import pl.edu.pg.eti.ksg.op.javka.World;
import pl.edu.pg.eti.ksg.op.javka.Point;

import java.awt.*;

public class Human extends Animal {
    private static final int MOV_RAN = 1;
    private static final double MOV_PTS = 1;
    private static final int STRENGTH = 5;
    private static final int INITIATIVE = 4;
    private Direction moveDirection;
    private Ability ability;

    public Human(World world, Point position, int age) {
        super(OrganismType.HUMAN, world, position, age, STRENGTH, INITIATIVE);
        this.setMovementRange(MOV_RAN);
        this.setMovementPoints(MOV_PTS);
        moveDirection = Direction.ENO_DIRECTION;
        setImage("human.jpg");
        ability = new Ability();
    }

    private void Purification() {
        RandomField(getPosition());//blocking illegal moves
        int x = getPosition().getX();
        int y = getPosition().getY();
        Organism tmpOrganism = null;
        for (int i = 0; i < 6; i++) {
            if (i == 0 && !IsDirectionLocked(Direction.EDOWN))
                tmpOrganism = getWorld().CheckField(new Point(x, y + 1));
            else if (i == 1 && !IsDirectionLocked(Direction.EUP))
                tmpOrganism = getWorld().CheckField(new Point(x, y - 1));
            if(getWorld().getIsHexagonal()){
                if (i == 2 && !IsDirectionLocked(Direction.ELEFT_DOWN)){
                    if(x%2==1)tmpOrganism = getWorld().CheckField(new Point(x-1, y));
                    else tmpOrganism = getWorld().CheckField(new Point(x-1, y+1));
                }
                else if (i == 3 && !IsDirectionLocked(Direction.ELEFT_UP))
                    if(x%2==0)tmpOrganism = getWorld().CheckField(new Point(x-1, y));
                    else tmpOrganism = getWorld().CheckField(new Point(x-1, y-1));
                else if (i == 4 && !IsDirectionLocked(Direction.ERIGHT_UP))
                    if(x%2==0)tmpOrganism = getWorld().CheckField(new Point(x+1, y));
                    else tmpOrganism = getWorld().CheckField(new Point(x+1, y-1));
                else if (i == 5 && !IsDirectionLocked(Direction.ERIGHT_DOWN))
                    if(x%2==0)tmpOrganism = getWorld().CheckField(new Point(x+1, y + 1));
                    else tmpOrganism = getWorld().CheckField(new Point(x+1, y));
            }
            else {
            if (i == 2 && !IsDirectionLocked(Direction.ELEFT))
                    tmpOrganism = getWorld().CheckField(new Point(x - 1, y));
            else if (i == 3 && !IsDirectionLocked(Direction.ERIGHT))
                tmpOrganism = getWorld().CheckField(new Point(x + 1, y));
            }
            if (tmpOrganism != null) {
                getWorld().DeleteOrganism(tmpOrganism);
                Comment.AddComment(OrganismToString() + " burns to ashes "
                        + tmpOrganism.OrganismToString());
            }
        }
    }

    @Override
    protected Point ScheduleMove() {
        int x = getPosition().getX();
        int y = getPosition().getY();
        RandomField(getPosition());//blocks all illegal moves
        if (moveDirection == Direction.ENO_DIRECTION ||
                IsDirectionLocked(moveDirection)) return getPosition();
        else {
            if (moveDirection == Direction.EDOWN) return new Point(x, y + 1);
            if (moveDirection == Direction.EUP) return new Point(x, y - 1);
            if (moveDirection == Direction.ELEFT) return new Point(x - 1, y);
            if (moveDirection == Direction.ERIGHT) return new Point(x + 1, y);
            if(moveDirection== Direction.ELEFT_DOWN){
                if(x%2==1)return new Point(x-1, y);
                else return new Point(x-1, y+1);
            }if(moveDirection== Direction.ELEFT_UP){
                if(x%2==0)return new Point(x-1, y);
                else return new Point(x-1, y-1);
            }if(moveDirection== Direction.ERIGHT_UP){
                if(x%2==0)return new Point(x+1, y);
                else return new Point(x+1, y-1);
            }if(moveDirection== Direction.ERIGHT_DOWN){
                if(x%2==0)return new Point(x+1, y+1);
                else return new Point(x+1, y);
            }
            else return new Point(x, y);
        }
    }

    @Override
    public void Action() {
        if (ability.getIsActive()) {
            Comment.AddComment(OrganismToString() + " Purification activated for: "
                    + ability.getActiveTime() + " turns)");
            Purification();
        }
        for (int i = 0; i < getMovementRange(); i++) {
            Point nextPosition = ScheduleMove();

            if (getWorld().IsFieldOccupied(nextPosition)
                    && getWorld().CheckField(nextPosition) != this) {
                Collision(getWorld().CheckField(nextPosition));
                break;
            } else if (getWorld().CheckField(nextPosition) != this) MakingMove(nextPosition);
            if (ability.getIsActive()) Purification();
        }
        moveDirection = Direction.ENO_DIRECTION;
        ability.CheckConditions();
    }

    @Override
    public String OrganismTypeToString() {
        return "Human";
    }

    public Ability getAbility() {
        return ability;
    }

    public void setMoveDirection(Direction moveDirection) {
        this.moveDirection = moveDirection;
    }

    public class Ability {
        private final int ACTIVE_TIME = 5;
        private final int COOLDOWN = 10;
        private boolean canActivate;
        private boolean isActive;
        private int activeTime;
        private int cooldown;

        public Ability() {
            cooldown = 0;
            activeTime = 0;
            isActive = false;
            canActivate= true;
        }

        public void CheckConditions() {
            if (cooldown > 0) cooldown--;
            if (activeTime > 0) activeTime--;
            if (activeTime == 0) Deactivate();
            if (cooldown == 0) canActivate = true;
        }

        public void Activate() {
            if (cooldown == 0) {
                isActive = true;
                canActivate = false;
                cooldown = COOLDOWN;
                activeTime = ACTIVE_TIME;
            }
        }

        public void Deactivate() {
            isActive = false;
        }

        public boolean getCanActivate() {
            return canActivate;
        }

        public void setCanActivate(boolean canActivate) {
            this.canActivate = canActivate;
        }

        public boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public int getActiveTime() {
            return activeTime;
        }

        public void setActiveTime(int activeTime) {
            this.activeTime = activeTime;
        }

        public int getCooldown() {
            return cooldown;
        }

        public void setCooldown(int cooldown) {
            this.cooldown = cooldown;
        }
    }
}