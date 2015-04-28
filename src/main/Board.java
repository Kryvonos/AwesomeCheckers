package main;

import javax.swing.*;
import java.awt.*;


public class Board extends JPanel {

    private int cellsPerRow;
    private int size;
    public Board(int size, int cellsPerRow){
        this.size = size;
        this.cellsPerRow = cellsPerRow;
        setPreferredSize(new Dimension(size, size));
    }



    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        double cellSize = size/cellsPerRow;

        for (int i = 0; i < cellsPerRow; i++) {
            for (int j = 0; j < cellsPerRow; j++) {
                g2.setColor(((j +i)% 2 == 0) ? Color.BLACK : Color.white );
                g2.fillRect((int) (j * cellSize), (int) (i * cellSize), (int) cellSize, (int) cellSize);
            }
        }
    }





}
