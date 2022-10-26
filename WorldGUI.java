package pl.edu.pg.eti.ksg.op.javka;

import pl.edu.pg.eti.ksg.op.javka.animals.Human;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class WorldGUI implements ActionListener, KeyListener {
    private Toolkit toolkit;
    private Dimension dimension;
    private JFrame jFrame;
    private JMenu menu;
    final private JMenuItem newGame, load, save, exit, Hexagonal;
    private BoardGraphics boardGraphics = null;
    private CommentGraphics commentGraphics = null;
    private Info info = null;
    private JPanel mainPanel;
    private final int GAP;
    private World world;

    public WorldGUI(String title) {
        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        GAP = dimension.height / 100;

        jFrame = new JFrame(title);
        jFrame.setBounds(0, 0, 1600, 800);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("New game");
        Hexagonal = new JMenuItem("Hexagonal");
        load = new JMenuItem("Load game");
        save = new JMenuItem("Save game");
        exit = new JMenuItem("Exit");
        newGame.addActionListener(this);
        Hexagonal.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
        menu.add(newGame);
        menu.add(Hexagonal);
        menu.add(load);
        menu.add(save);
        menu.add(exit);
        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);
        jFrame.setLayout(new CardLayout());
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.ORANGE);
        mainPanel.setLayout(null);
        jFrame.addKeyListener(this);
        jFrame.add(mainPanel);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            Comment.ResetComment();
            int sizeX = 10;
            int sizeY = 10;
            boolean isHexagonal = false;
            double density = Double.parseDouble(JOptionPane.showInputDialog
                    (jFrame, "Density(from 0 to 10)", "5"));
            density = density/10;
            world = new World(sizeX, sizeY, this, isHexagonal);
            world.GenerateWorld(density);
            if (boardGraphics != null)
                mainPanel.remove(boardGraphics);
            if (commentGraphics != null)
                mainPanel.remove(commentGraphics);
            if (info != null)
                mainPanel.remove(info);
            startGame(isHexagonal);
        }
        if(e.getSource() == Hexagonal){
            Comment.ResetComment();
            int sizeX=7;
            int sizeY=7;

            boolean isHexagonal =true;
            double density = Double.parseDouble(JOptionPane.showInputDialog
                    (jFrame, "Density(from 0 to 10)", "5"));
            density = density/10;
            world = new World(sizeX, sizeY, this,isHexagonal);
            world.GenerateWorld(density);
            if (boardGraphics != null)
                mainPanel.remove(boardGraphics);
            if (commentGraphics != null)
                mainPanel.remove(commentGraphics);
            if (info != null)
                mainPanel.remove(info);
            startGame(isHexagonal);
        }
        if (e.getSource() == load) {
            Comment.ResetComment();
            String nameOfFile = JOptionPane.showInputDialog(jFrame, "Path to the save", "save");
            world = World.OpenWorld(nameOfFile);
            world.setWorldGUI(this);
            boardGraphics = new BoardGraphics(world);
            commentGraphics = new CommentGraphics();
            info = new Info();
            if (boardGraphics != null)
                mainPanel.remove(boardGraphics);
            if (commentGraphics != null)
                mainPanel.remove(commentGraphics);
            if (info != null)
                mainPanel.remove(info);
            startGame(getWorld().getIsHexagonal());
        }
        if (e.getSource() == save) {
            String path = JOptionPane.showInputDialog(jFrame, "Name of the file", "save");
            world.SaveWorld(path);
            Comment.AddComment("Game saved correctly ");
            commentGraphics.refreshComments();
        }
        if (e.getSource() == exit) {
            jFrame.dispose();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (world != null && world.isPause()) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {

            } else if (world.getIsHumanAlive()) {


                if (keyCode == KeyEvent.VK_UP||keyCode == KeyEvent.VK_W) {world.getHuman().setMoveDirection(Organism.Direction.EUP);}
                else if (keyCode == KeyEvent.VK_DOWN||keyCode == KeyEvent.VK_S) {world.getHuman().setMoveDirection(Organism.Direction.EDOWN);}
                else if (keyCode == KeyEvent.VK_Q)world.getHuman().setMoveDirection(Organism.Direction.ELEFT_UP);
                else if (keyCode == KeyEvent.VK_A)world.getHuman().setMoveDirection(Organism.Direction.ELEFT_DOWN);
                else if (keyCode == KeyEvent.VK_E)world.getHuman().setMoveDirection(Organism.Direction.ERIGHT_UP);
                else if (keyCode == KeyEvent.VK_D)world.getHuman().setMoveDirection(Organism.Direction.ELEFT_DOWN);
                else if (keyCode == KeyEvent.VK_LEFT) {world.getHuman().setMoveDirection(Organism.Direction.ELEFT);}
                else if (keyCode == KeyEvent.VK_RIGHT) {world.getHuman().setMoveDirection(Organism.Direction.ERIGHT);}
                else if (keyCode == KeyEvent.VK_P) {
                    Human.Ability tempAbility = world.getHuman().getAbility();
                    if (tempAbility.getCanActivate()) {
                        tempAbility.Activate();
                        Comment.AddComment("Purification activated ");

                    } else if (tempAbility.getIsActive()) {
                        Comment.AddComment("Can't activate. Time left" +tempAbility.getActiveTime() + " turns)");
                        commentGraphics.refreshComments();
                        return;
                    } else {
                        Comment.AddComment("Wait can't activate");
                        commentGraphics.refreshComments();
                        return;
                    }
                } else {
                    Comment.AddComment("\nWrong key");
                    commentGraphics.refreshComments();
                    return;
                }
            } else if (!world.getIsHumanAlive() && (keyCode == KeyEvent.VK_UP ||
                    keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_P||
                    keyCode == KeyEvent.VK_Q || keyCode == KeyEvent.VK_W||
                    keyCode == KeyEvent.VK_E || keyCode == KeyEvent.VK_A||
                    keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_D)) {
                Comment.AddComment("Human has died tragically");
                commentGraphics.refreshComments();
                return;
            } else {
                Comment.AddComment("\nWrong key");
                commentGraphics.refreshComments();
                return;
            }
            Comment.ResetComment();
            world.setPause(false);
            world.MakeTurn();
            refreshWorld();
            world.setPause(true);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    private class BoardGraphics extends JPanel {
        private int sizeX;
        private  int sizeY;
        private Board[][] board;
        private static final int SIDES = 6;
        private static final int SIDE_LENGTH = 50;
        public static final int LENGTH = 95;
        public static final int WIDTH = 105;

        public BoardGraphics(World world) {
            super();
            setBounds(mainPanel.getX() + GAP, mainPanel.getY() + GAP,
                    mainPanel.getHeight() * 5 / 6 - GAP, mainPanel.getHeight() * 5 / 6 - GAP);
            this.sizeX = world.getSizeX();
            this.sizeY = world.getSizeY();
            board = new Board[sizeY][sizeX];
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    board[i][j] = new Board(j, i);
                    board[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                           if (e.getSource() instanceof Board) {
                                Board tmpBoard = (Board) e.getSource();
                                if (tmpBoard.isEmpty) {
                                    OrganismList organismList = new OrganismList(tmpBoard.getX() + jFrame.getX(),
                                                    tmpBoard.getY() + jFrame.getY(),
                                                    new Point(tmpBoard.getPosX(), tmpBoard.getPosY()));
                                }
                            }
                        }
                    });
                }
            }
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    this.add(board[i][j]);
                }
            }
            this.setLayout(new GridLayout(sizeY, sizeX));
        }
        public BoardGraphics(World world, boolean isHexagonal){
            setLayout(null);
            setBackground(Color.ORANGE);
            MakingMap(world, isHexagonal);
        }
        public void MakingMap(World world, boolean isHexagonal){

            setBounds(mainPanel.getX() + GAP, mainPanel.getY() + GAP,
                    700, 670);
            this.sizeX = world.getSizeX();
            this.sizeY = world.getSizeY();
            int offsetX = 50;
            int offsetY = 50;
            board = new Board[sizeY][sizeX];
            for(int i = 0; i < sizeY; i++) {
                for(int j = 0; j < sizeX; j++){
                    board[i][j] = new Board(j,i, WIDTH, LENGTH);
                    board[i][j].addActionListener(e -> {
                        if (e.getSource() instanceof Board) {
                            Board tmpBoard = (Board) e.getSource();
                            if (tmpBoard.isEmpty) {
                                OrganismList organismList = new OrganismList(tmpBoard.getX() + jFrame.getX(),
                                        tmpBoard.getY() + jFrame.getY(),
                                        new Point(tmpBoard.getPosX(), tmpBoard.getPosY()));
                            }
                        }
                    });
                    add(board[i][j]);
                    board[i][j].setBounds(offsetY, offsetX, 105, 95);
                    offsetX += 87;
                }
                if(i%2 == 0) {
                    offsetX = 7;
                } else {
                    offsetX = 50;
                }
                offsetY += 76;
            }
        }
        private class Board extends JButton {
            private boolean isEmpty;
            private Color color;
            private final int posX;
            private final int posY;
            public Board(int X, int Y) {
                super();
                color = Color.WHITE;
                setBackground(color);
                isEmpty = true;
                posX = X;
                posY = Y;
            }
            public Board(int X, int Y, int WIDTH, int LENGTH) {

                setContentAreaFilled(false);
                setFocusPainted(true);
                setBorderPainted(false);
                setPreferredSize(new Dimension(WIDTH, LENGTH));
                posX = X;
                posY =Y;
            }
            @Override
            public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Polygon hex = new Polygon();
                    for (int i = 0; i < SIDES; i++) {
                        hex.addPoint((int) (50 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                                (int) (50 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
                    }
                    if(getWorld().getIsHexagonal()) {
                        g.drawPolygon(hex);
                    }
            }
            public void setEmpty(boolean empty) {
                isEmpty = empty;
            }
            public void setColor(Color color) {
                this.color = color;
                setBackground(color);
            }
            public int getPosX() {
                return posX;
            }
            public int getPosY() {
                return posY;
            }
        }
        public void refreshBoard() {
            URL url;
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    Organism tmpOrganism = world.getBoard()[i][j];
                    if (tmpOrganism != null) {
                        board[i][j].setEmpty(false);
                        url = getClass().getResource("icons/"+tmpOrganism.getImage());
                        board[i][j].setIcon(new ImageIcon(url));
                        board[i][j].setVisible(true);
                    } else {
                        board[i][j].setEmpty(true);
                        board[i][j].setEnabled(true);
                        board[i][j].setColor(Color.WHITE);
                        url = getClass().getResource("icons/empty.jpg");
                        board[i][j].setIcon(new ImageIcon(url));
                    }
                }
            }
        }

    }

    private class CommentGraphics extends JPanel {
        private String text;

        private JTextArea textArea;

        public CommentGraphics() {
            super();
            setBounds(boardGraphics.getX() + boardGraphics.getWidth() + GAP,
                    mainPanel.getY() + GAP,
                    mainPanel.getWidth() - boardGraphics.getWidth() - GAP * 3,
                    mainPanel.getHeight() * 5 / 6 - GAP);
            text = Comment.getText();
            textArea = new JTextArea(text);
            textArea.setEditable(false);
            setLayout(new CardLayout());

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }

        public void refreshComments() {
            text =  Comment.getText();
            textArea.setText(text);
        }
    }
    private class OrganismList extends JFrame {
        private String[] organismList;
        private Organism.OrganismType[] organismTypeList;
        private JList jList;

        public OrganismList(int x, int y, Point point) {
            super("All organisms");
            setBounds(x, y, 100, 300);
            organismList = new String[]{"Sosnowsky's Hogweed", "Guarana", "Sow thistle", "Grass",
                    "Belladonna", "Antelope", "Fox", "Sheep", "Wolf", "Turtle"};
            organismTypeList = new Organism.OrganismType[]{Organism.OrganismType.SOSNOWSKY_HOGWEED,
                    Organism.OrganismType.GUARANA, Organism.OrganismType.SOW_THISTLE, Organism.OrganismType.GRASS,
                    Organism.OrganismType.BELLADONNA, Organism.OrganismType.ANTELOPE,
                    Organism.OrganismType.FOX,
                    Organism.OrganismType.SHEEP, Organism.OrganismType.WOLF,
                    Organism.OrganismType.TURTLE
            };

            jList = new JList(organismList);
            jList.setVisibleRowCount(organismTypeList.length);
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jList.addListSelectionListener(e -> {
                Organism tmpOrganism = SpawnOrganism.CreateOrganism(organismTypeList[jList.getSelectedIndex()], point, world);
                world.AddOrganism(tmpOrganism);
                Comment.AddComment("New organism added " + tmpOrganism.OrganismToString());
                refreshWorld();
                dispose();

            });

            JScrollPane sp = new JScrollPane(jList);
            add(sp);

            setVisible(true);
        }
    }

    private class Info extends JPanel {
        private final int BOX_COUNT = 3;
        private JButton[] jButtons;

        public Info() {
            super();
             final String name = "Wiktor Czetyrbok";
            setBounds(mainPanel.getX() + GAP, mainPanel.getHeight() * 5 / 6 + GAP+50,
                    mainPanel.getWidth() - GAP * 2,
                    mainPanel.getHeight() / 6 - 2 * GAP);
            setBackground(Color.ORANGE);
            setLayout(new FlowLayout(FlowLayout.CENTER));
            jButtons = new JButton[BOX_COUNT];
            jButtons[0] = new JButton(name);
            jButtons[0].setBackground(Color.CYAN);

            jButtons[1] = new JButton("Press arrows to move\n");
            jButtons[1].setBackground(Color.BLUE);
            jButtons[2] = new JButton("P - purification\n Enter - next turn");
            jButtons[2].setBackground(Color.CYAN);


            for (int i = 0; i < BOX_COUNT; i++) {
                jButtons[i].setEnabled(false);
                add(jButtons[i]);
            }


        }
    }
    private void startGame(boolean isHexagonal) {
        if(isHexagonal)
            boardGraphics = new BoardGraphics(world, isHexagonal);
       else
            boardGraphics = new BoardGraphics(world);


        mainPanel.add(boardGraphics);

        commentGraphics = new CommentGraphics();
        mainPanel.add(commentGraphics);

        info = new Info();
        mainPanel.add(info);

        refreshWorld();
    }

    public void refreshWorld() {
        boardGraphics.refreshBoard();
        commentGraphics.refreshComments();
        SwingUtilities.updateComponentTreeUI(jFrame);
        jFrame.requestFocusInWindow();
    }

    public World getWorld() {
        return world;
    }

}