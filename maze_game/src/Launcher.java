import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        FileHandler f1 = new FileHandler();
        System.out.println(args.length);
        if (args.length == 0) {
            System.out.println("Missing file location. Example: java Launcher Maze002.txt");
            System.exit(1);
        }
        String mazeFile = args[0];
        boolean gui = false;
        if (args.length == 2) {
            if (args[1].equals("GUI")) {
                gui = true;
            }
        } else if (args.length > 2) {
            System.out.println("Too many arguments, max arguments: java Launcher Maze002.txt GUI");
            System.exit(2);
        }

        try {
            MazeGame mazeGame = new MazeGame(f1.load(mazeFile), gui);
            mazeGame.game();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
