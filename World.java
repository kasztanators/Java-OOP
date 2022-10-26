package pl.edu.pg.eti.ksg.op.javka;

import pl.edu.pg.eti.ksg.op.javka.animals.Human;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class World {
    private int sizeX;
    private int sizeY;
    private int turnCount;
    private Organism[][] board;
    private boolean isHumanAlive;
    private boolean isGameEnd;
    private boolean pause;
    private ArrayList<Organism> organisms;
    private Human human;
    private WorldGUI worldGUI;
    private boolean isHexagonal;

    public World(WorldGUI worldGUI) {
        setIsHexagonal(false);
        this.sizeX = 0;
        this.sizeY = 0;
        turnCount = 0;
        isHumanAlive = true;
        isGameEnd = false;
        pause = true;
        organisms = new ArrayList<>();
        this.worldGUI = worldGUI;
    }

    public World(int sizeX, int sizeY, WorldGUI worldGUI, Boolean isHexagonal) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        setIsHexagonal(isHexagonal);
        turnCount = 0;
        isHumanAlive = true;
        isGameEnd = false;
        pause = true;
        board = new Organism[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                board[i][j] = null;
            }
        }
        organisms = new ArrayList<>();
        this.worldGUI = worldGUI;
    }


    public void SaveWorld(String path) {
        try {
            path += ".txt";
            File file = new File(path);
            file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            pw.print(isHexagonal +" ");
            pw.print(sizeX + " ");
            pw.print(sizeY + " ");
            pw.print(turnCount + " ");
            pw.print(isHumanAlive + " ");
            pw.print(isGameEnd + "\n");
            for (int i = 0; i < organisms.size(); i++) {
                pw.print(organisms.get(i).getOrganismType() + " ");
                pw.print(organisms.get(i).getPosition().getX() + " ");
                pw.print(organisms.get(i).getPosition().getY() + " ");
                pw.print(organisms.get(i).getStrength() + " ");
                pw.print(organisms.get(i).getAge() + " ");
                pw.print(organisms.get(i).getIsDead());
                if (organisms.get(i).getOrganismType() == Organism.OrganismType.HUMAN) {
                    pw.print(" " + human.getAbility().getActiveTime() + " ");
                    pw.print(human.getAbility().getCooldown() + " ");
                    pw.print(human.getAbility().getIsActive() + " ");
                    pw.print(human.getAbility().getCanActivate());
                }
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static World OpenWorld(String path) {
        try {
            path += ".txt";
            File file = new File(path);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            boolean isHexagonal = Boolean.parseBoolean(properties[0]);
            int sizeX = Integer.parseInt(properties[1]);
            int sizeY = Integer.parseInt(properties[2]);
            World tmpWorld = new World(sizeX, sizeY, null, isHexagonal);
            int turnCount = Integer.parseInt(properties[3]);
            tmpWorld.turnCount = turnCount;
            boolean isHumanAlive = Boolean.parseBoolean(properties[4]);
            tmpWorld.isHumanAlive = isHumanAlive;
            boolean isGameEnd = Boolean.parseBoolean(properties[5]);
            tmpWorld.isGameEnd = isGameEnd;
            tmpWorld.human = null;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                Organism.OrganismType organismType = Organism.OrganismType.valueOf(properties[0]);
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);

                Organism tmpOrganism = SpawnOrganism.CreateOrganism(organismType,  new Point(x, y),tmpWorld);
                int strength = Integer.parseInt(properties[3]);
                tmpOrganism.setStrength(strength);
                int age = Integer.parseInt(properties[4]);
                tmpOrganism.setAge(age);
                boolean isDead = Boolean.parseBoolean(properties[5]);
                tmpOrganism.setIsDead(isDead);

                if (organismType== Organism.OrganismType.HUMAN) {
                    tmpWorld.human = (Human) tmpOrganism;
                    int activeTime = Integer.parseInt(properties[6]);
                    tmpWorld.human.getAbility().setActiveTime(activeTime);
                    int cooldown = Integer.parseInt(properties[7]);
                    tmpWorld.human.getAbility().setCooldown(cooldown);
                    boolean isActive = Boolean.parseBoolean(properties[8]);
                    tmpWorld.human.getAbility().setIsActive(isActive);
                    boolean canAcitivate = Boolean.parseBoolean(properties[9]);
                    tmpWorld.human.getAbility().setCanActivate(canAcitivate);
                }
                tmpWorld.AddOrganism(tmpOrganism);
            }
            scanner.close();
            return tmpWorld;
        } catch (
                IOException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public void GenerateWorld(double density) {
        int organismCount = (int) Math.floor(sizeX * sizeY * density);
        //creating human to the board
        Point position = RandomNotOccupied();
        Organism tmpOrganism = SpawnOrganism.CreateOrganism(Organism.OrganismType.HUMAN,  position, this);
        AddOrganism(tmpOrganism);
        human = (Human) tmpOrganism;
        //adding all others organisms
        for (int i = 0; i < organismCount - 1; i++) {
            position = RandomNotOccupied();
            if (position != new Point(-1, -1)) {
                AddOrganism(SpawnOrganism.CreateOrganism(Organism.RandomType(),  position,this));
            } else return;
        }
    }

    public void MakeTurn() {
        if (isGameEnd) return;
        turnCount++;
        Comment.AddComment("Human strength: "+getHuman().getStrength());
        Comment.AddComment("\nTurn: " + turnCount);
//https://www.geeksforgeeks.org/collections-sort-java-examples/
        SortOrganisms();
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getAge() != turnCount
                    && !organisms.get(i).getIsDead()) {
                organisms.get(i).Action();
            }
        }
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getIsDead()) {
                organisms.remove(i);
                i--;
            }
        }
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setIsSowing(false);
        }
    }

    private void SortOrganisms() {
        Collections.sort(organisms, (o1, o2) -> {
            if (o1.getInitiative() != o2.getInitiative())
                return Integer.compare(o2.getInitiative(), o1.getInitiative());
            else
                return Integer.compare(o1.getAge(), o2.getAge());
        });
    }

    public Point RandomNotOccupied() {
        Random rand = new Random();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (board[i][j] == null) {
                    while (true) {
                        int x = rand.nextInt(sizeX);
                        int y = rand.nextInt(sizeY);
                        if (board[y][x] == null) return new Point(x, y);
                    }
                }
            }
        }
        return new Point(-1, -1);
    }

    public boolean IsFieldOccupied(Point field) {
        if (board[field.getY()][field.getX()] == null) return false;
        else return true;
    }

    public Organism CheckField(Point field) {
        return board[field.getY()][field.getX()];
    }

    public void AddOrganism(Organism organism) {
        organisms.add(organism);
        board[organism.getPosition().getY()][organism.getPosition().getX()] = organism;
    }

    public void DeleteOrganism(Organism organism) {
        board[organism.getPosition().getY()][organism.getPosition().getX()] = null;
        organism.setIsDead(true);
        if (organism.getOrganismType() == Organism.OrganismType.HUMAN) {
            isHumanAlive = false;
            human = null;
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public Organism[][] getBoard() {
        return board;
    }

    public boolean getIsHumanAlive() {
        return isHumanAlive;
    }

    public Human getHuman() {
        return human;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setWorldGUI(WorldGUI worldGUI) {
        this.worldGUI = worldGUI;
    }

    public boolean getIsHexagonal() {
        return isHexagonal;
    }
    public void setIsHexagonal(boolean isHexagonal){
        this.isHexagonal = isHexagonal;
    }
}
