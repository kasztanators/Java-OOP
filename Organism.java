package pl.edu.pg.eti.ksg.op.javka;

import java.util.Random;
public abstract class Organism {
    public enum OrganismType{
        HUMAN,
        WOLF,
        SHEEP,
        FOX,
        TURTLE,
        ANTELOPE,
        GRASS,
        SOW_THISTLE,
        GUARANA,
        BELLADONNA,
        SOSNOWSKY_HOGWEED
    }
    public enum  Direction
    {
        ELEFT(0),
        ERIGHT(1),
        EUP(2),
        EDOWN(3),
        ELEFT_UP(4),
        ELEFT_DOWN(5),
        ERIGHT_UP(6),
        ERIGHT_DOWN(7),
        ENO_DIRECTION(8);


        private final int value;

        Direction(int value){this.value = value;}
        public int getValue(){return value;}
    }
    private int strength;
    private int initiative;
    private int age;
    private String image;
    private boolean isDead;
    private boolean[] direction;
    private boolean isSowing;
    private World world;
    private Point position;
    private OrganismType organismType;
    private double sowingChance;
    private static final int HOW_MANY_ORGANISM =11;

    public abstract String OrganismTypeToString();
    public abstract void Action();
    public abstract void Collision(Organism other);
    public abstract boolean IsAnimal();

    public Organism(OrganismType organismType, World world, Point position, int age, int strength, int initiative){
        this.organismType = organismType;
        this.world = world;
        this.position = position;
        this.age = age;
        this.strength = strength;
        this.initiative= initiative;
        isDead = false;
        direction = new boolean[]{true,true,true,true,true,true,true,true};

    }
    public boolean SpecialAttackEngine(Organism attacker, Organism enemy){return false;}
    public String OrganismToString(){
        return (OrganismTypeToString());
    }
    public void MakingMove(Point nextPosition) {
        int x = nextPosition.getX();
        int y = nextPosition.getY();
        world.getBoard()[position.getY()][position.getX()] = null;
        world.getBoard()[y][x] = this;
        position.setX(x);
        position.setY(y);
    }
    static OrganismType RandomType() {// Random type other that human
        Random rand = new Random();
        int tmp = rand.nextInt(HOW_MANY_ORGANISM - 1);//-1 lack of human
        if (tmp == 0) return OrganismType.ANTELOPE;
        if (tmp == 1) return OrganismType.BELLADONNA;
        if (tmp == 2) return OrganismType.FOX;
        if (tmp == 3) return OrganismType.GRASS;
        if (tmp == 4) return OrganismType.GUARANA;
        if (tmp == 5) return OrganismType.SHEEP;
        if (tmp == 6) return OrganismType.SOW_THISTLE;
        if (tmp == 7) return OrganismType.WOLF;
        if (tmp == 8) return OrganismType.SOSNOWSKY_HOGWEED;
        else return OrganismType.TURTLE;
    }
//locking directions
    public Point RandomField(Point position) {
        UnlockAllDirections();
        int posX = position.getX();
        int posY = position.getY();
        int sizeX = world.getSizeX();
        int sizeY = world.getSizeY();
        int possibleDirections = 0;
        if(!getWorld().getIsHexagonal()) {
            if (posX == 0) LockDirection(Direction.ELEFT);
            else possibleDirections++;
            if (posX == sizeX - 1) LockDirection(Direction.ERIGHT);
            else possibleDirections++;
        }
        //bart for hexagonal map
        else{
            if((posY == sizeY-1&& posX%2==0)||posX==0)LockDirection(Direction.ELEFT_DOWN);
        else possibleDirections++;
            if((posY == sizeY-1&& posX%2==0)||posX==sizeX-1)LockDirection(Direction.ERIGHT_DOWN);
            else possibleDirections++;
            if((posY == 0&& posX%2==1)||posX==sizeX-1)LockDirection(Direction.ERIGHT_UP);
        else possibleDirections++;
            if((posY == 0&& posX%2==1)||posX==0)LockDirection(Direction.ELEFT_UP);
            else possibleDirections++;
        }
        if (posY == 0) LockDirection(Direction.EUP);
        else possibleDirections++;
        if (posY == sizeY - 1) LockDirection(Direction.EDOWN);
        else possibleDirections++;

        if (possibleDirections == 0) return position;
        while (true) {
            Random rand = new Random();
            if(!getWorld().getIsHexagonal()) {
                int upperbound = 100;
                int tmpRandomization = rand.nextInt(upperbound);
                if (tmpRandomization < 25 && !IsDirectionLocked(Direction.ELEFT))
                    return new Point(posX - 1, posY);
                else if (tmpRandomization >= 25 && tmpRandomization < 50 && !IsDirectionLocked(Direction.ERIGHT))
                    return new Point(posX + 1, posY);
                else if (tmpRandomization >= 50 && tmpRandomization < 75 && !IsDirectionLocked(Direction.EUP))
                    return new Point(posX, posY - 1);
                else if (tmpRandomization >= 75 && !IsDirectionLocked(Direction.EDOWN))
                    return new Point(posX, posY + 1);
            }
            else{
                int upperbound=5;
                int tmpRandomization = rand.nextInt(upperbound);
                if (tmpRandomization==0 && !IsDirectionLocked(Direction.EUP))
                    return new Point(posX, posY - 1);
                else if (tmpRandomization ==1 && !IsDirectionLocked(Direction.EDOWN))
                    return new Point(posX, posY + 1);
                else if (tmpRandomization ==2 && !IsDirectionLocked(Direction.ERIGHT_DOWN)){
                    if(getPosition().getX()%2==0)return new Point(posX+1, posY+1);
                    else return new Point(posX+1, posY);
                }
                else if (tmpRandomization ==3 && !IsDirectionLocked(Direction.ERIGHT_UP)){
                    if(getPosition().getX()%2==0)return new Point(posX+1, posY);
                    else return new Point(posX+1, posY-1);
                }
                else if (tmpRandomization ==4 && !IsDirectionLocked(Direction.ELEFT_UP)){
                    if(getPosition().getX()%2==0)return new Point(posX-1, posY);
                    else return new Point(posX-1, posY-1);
                }
                else if (tmpRandomization ==5 && !IsDirectionLocked(Direction.ELEFT_DOWN)){
                    if(getPosition().getX()%2==1)return new Point(posX-1, posY);
                    else return new Point(posX-1, posY+1);
                }

            }
        }
    }
//is direction possible and it's not occupied
    public Point RandomNotOccupiedField(Point position) {
        UnlockAllDirections();
        int posX = position.getX();
        int posY = position.getY();
        int sizeX = world.getSizeX();
        int sizeY = world.getSizeY();
        int possibleDirections = 0;
    if(!getWorld().getIsHexagonal()) {
       if (posX == 0) LockDirection(Direction.ELEFT);
      else {
         if (world.IsFieldOccupied(new Point(posX - 1, posY)) == false) possibleDirections++;
         else LockDirection(Direction.ELEFT);
      }

       if (posX == sizeX - 1) LockDirection(Direction.ERIGHT);
       else {
            if (world.IsFieldOccupied(new Point(posX + 1, posY)) == false) possibleDirections++;
          else LockDirection(Direction.ERIGHT);
       }
    }
    else{
        if (posX == 0||(posY == 0&& posX%2==1)) LockDirection(Direction.ELEFT_UP);
        else {
            if (world.IsFieldOccupied(new Point(posX -1, posY)) == false&&posX%2==0) possibleDirections++;
            else if (world.IsFieldOccupied(new Point(posX-1, posY-1)) == false&&posX%2==1) possibleDirections++;
            else LockDirection(Direction.ELEFT_UP);
        }
        if ((posY == sizeY-1&& posX%2==0)||posX==0) LockDirection(Direction.ELEFT_DOWN);
        else {
            if (world.IsFieldOccupied(new Point(posX -1, posY)) == false&&posX%2==1) possibleDirections++;
            else if(world.IsFieldOccupied(new Point(posX -1, posY+1)) == false&&posX%2==0)possibleDirections++;
            else LockDirection(Direction.ELEFT_DOWN);
        }
        if ((posY == 0&& posX%2==1)||posX==sizeX-1) LockDirection(Direction.ERIGHT_UP);
        else {
            if (world.IsFieldOccupied(new Point(posX + 1, posY)) == false&&posX%2==0) possibleDirections++;
            else if (world.IsFieldOccupied(new Point(posX + 1, posY-1)) == false&&posX%2==1) possibleDirections++;
            else LockDirection(Direction.ERIGHT_UP);
        }
        if ((posY == sizeY-1&& posX%2==0)||posX==sizeX-1) LockDirection(Direction.ERIGHT_DOWN);
        else {
            if (world.IsFieldOccupied(new Point(posX + 1, posY+1)) == false&&posX%2==0) possibleDirections++;
            else if (world.IsFieldOccupied(new Point(posX + 1, posY)) == false&&posX%2==1) possibleDirections++;
            else LockDirection(Direction.ERIGHT_DOWN);
        }
    }
    if (posY == 0) LockDirection(Direction.EUP);
    else {
        if (world.IsFieldOccupied(new Point(posX, posY - 1)) == false) possibleDirections++;
        else LockDirection(Direction.EUP);
    }

    if (posY == sizeY - 1) LockDirection(Direction.EDOWN);
    else {
        if (world.IsFieldOccupied(new Point(posX, posY + 1)) == false) possibleDirections++;
        else LockDirection(Direction.EDOWN);
    }
        if (possibleDirections == 0) return new Point(posX, posY);
        while (true) {
            Random rand = new Random();
            int upperbound = 100;
            int tmpRandomisation = rand.nextInt(upperbound);
            //moment to the left
            if (tmpRandomisation < 25 && !IsDirectionLocked(Direction.ELEFT))
                return new Point(posX - 1, posY);
                //movement to the right
            else if (tmpRandomisation >= 25 && tmpRandomisation < 50 && !IsDirectionLocked(Direction.ERIGHT))
                return new Point(posX + 1, posY);
                //upper
            else if (tmpRandomisation >= 50 && tmpRandomisation < 75 && !IsDirectionLocked(Direction.EUP))
                return new Point(posX, posY - 1);
                //down
            else if (tmpRandomisation >= 75 && !IsDirectionLocked(Direction.EDOWN))
                return new Point(posX, posY + 1);
        }
    }

    protected void LockDirection(Direction direction) {
        this.direction[direction.getValue()] = false;
    }

    protected void UnlockDirection(Direction direction) {
        this.direction[direction.getValue()] = true;
    }
//resetting directions
    protected void UnlockAllDirections() {
        UnlockDirection(Direction.ELEFT);
        UnlockDirection(Direction.ERIGHT);
        UnlockDirection(Direction.EUP);
        UnlockDirection(Direction.EDOWN);
        UnlockDirection(Direction.ERIGHT_DOWN);
        UnlockDirection(Direction.ERIGHT_UP);
        UnlockDirection(Direction.ELEFT_UP);
        UnlockDirection(Direction.ELEFT_DOWN);

    }

    protected boolean IsDirectionLocked(Direction direction) {
        return !(this.direction[direction.getValue()]);
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAge() {
        return age;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public boolean getIsSowing() {
        return isSowing;
    }

    public World getWorld() {
        return world;
    }

    public Point getPosition() {
        return position;
    }

    public OrganismType getOrganismType() {
        return organismType;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public void setIsSowing(boolean isSowing) {
        this.isSowing = isSowing;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setOrganismType(OrganismType organismType) {
        this.organismType = organismType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getSowingChance() {
        return sowingChance;
    }

    public void setSowingChance(double sowingChance) {
        this.sowingChance = sowingChance;
    }
}


