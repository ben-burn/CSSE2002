import javax.swing.*;
import java.awt.*;

public class MazeGame {
    private char[][] maze;
    private boolean gui;
    public MazeGame(char[][] maze, boolean gui) {
        this.maze = maze;
        this.gui = gui;
    }

    public void game() {
        System.out.println("Running maze game");
        if (gui) {
            initialise_gui();
        }
    }

    private void initialise_gui() {
        JFrame frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel grid = new JPanel(new GridLayout(this.maze.length, this.maze[0].length));
        Color wall = new Color(0, 0, 128);
        Color floor = new Color(34, 139, 34);
        Color start = new Color(255, 69, 0);
        Color end = new Color(75, 0, 130);
        Color player = new Color(255, 215, 0);
        for (int row = 0; row < this.maze.length; row++) {
            for (int column = 0; column < this.maze[row].length; column++) {
                JPanel block = new JPanel();

                JLabel str = new JLabel(String.valueOf(this.maze[row][column]), SwingConstants.CENTER);
                str.setOpaque(true);
                if (this.maze[row][column] == '#') {
                    str.setBackground(wall);
                } else if (this.maze[row][column] == 'S') {
                    str.setBackground(start);
                } else if (this.maze[row][column] == 'E') {
                    str.setBackground(end);
                } else {
                    str.setBackground(floor);
                }
                grid.add(str);
            }
        }

        frame.add(grid);
        frame.pack();
        frame.setVisible(true);
    }
}
