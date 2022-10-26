package pl.edu.pg.eti.ksg.op.javka;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HexagonPattern extends JPanel {

    private static final int sizeY = 8;
    private static final int sizeX = 8;
    private HexagonButton[][] hexButton = new HexagonButton[sizeY][sizeX];


    public HexagonPattern() {
        setLayout(null);
        initGUI();
    }


    public void initGUI() {
        int offsetX = 30;
        int offsetY = 50;

        for(int row = 0; row < sizeY; row++) {
            for(int col = 0; col < sizeX; col++){
                hexButton[row][col] = new HexagonButton(row, col);
                hexButton[row][col].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        HexagonButton clickedButton = (HexagonButton) e.getSource();

                    }
                });
                add(hexButton[row][col]);
                hexButton[row][col].setBounds(offsetY, offsetX, 105, 95);
                offsetX += 87;
            }
            if(row%2 == 0) {
                offsetX = -12;
            } else {
                offsetX = 30;
            }
            offsetY += 76;
        }
    }
    //Following class draws the Buttons
    class HexagonButton extends JButton {
      
        private static final int SIDES = 6;
        private static final int SIDE_LENGTH = 50;
        public static final int LENGTH = 95;
        public static final int WIDTH = 105;
        private int row = 0;
        private int col = 0;

        public HexagonButton(int row, int col) {
            setContentAreaFilled(false);
            setFocusPainted(true);
            setBorderPainted(false);
            setPreferredSize(new Dimension(WIDTH, LENGTH));
            this.row = row;
            this.col = col;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Polygon hex = new Polygon();
            for (int i = 0; i < SIDES; i++) {
                hex.addPoint((int) (50 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                        (int) (50 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
            }
            g.drawPolygon(hex);
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }
}