import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import io.FileInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FileHandler implements FileInterface {
    @Override
    public char[][] load(String filename) throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            line = reader.readLine();
            System.out.println(line);
            String[] mazeSize = line.split(" ", 2);
            int[] mazeDim = new int[2];
            mazeDim[0] = Integer.parseInt(mazeSize[0]);
            mazeDim[1] = Integer.parseInt(mazeSize[1]);
            // Not dynamically created - index out of bounds errors from low mazeDim
            char[][] maze = new char[mazeDim[0]][mazeDim[1]];
            int rows = 0;
            int columns = 0;
            while ((line = reader.readLine()) != null) {
                // Process the line here
                char[] lineToAdd = line.toCharArray();
                System.arraycopy(lineToAdd, 0, maze[rows], 0, lineToAdd.length);
                /*
                for (int column = 0; column < lineToAdd.length; column++) {
                    maze[rows][column] = lineToAdd[column];
                }
                */
                rows++;
                if (lineToAdd.length > columns) {
                    columns = lineToAdd.length;
                }
                System.out.println(lineToAdd);
            }
            System.out.flush();
            validateMaze(maze, mazeDim, rows, columns);
            System.out.println("Maze is valid");
            System.out.flush();
            return maze;
        } catch (MazeMalformedException e) {
            throw new MazeMalformedException();
        } catch (MazeSizeMissmatchException e) {
            throw new MazeSizeMissmatchException();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            throw new FileNotFoundException();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("mazeDim miss matched actual maze - could be mazeDim < actual");
            System.out.println(e);
            throw new MazeSizeMissmatchException();
        }
    }


    private static void validateMaze(char[][] maze, int[] mazeDim, int rows, int columns) throws MazeMalformedException,
            MazeSizeMissmatchException {
        if (mazeDim[0] % 2 == 0 || mazeDim[1] % 2 == 0) {
            System.out.println("Even mazeDim");
            throw new MazeMalformedException();
        }
        // Initialise array to hold length of rows
        int[] rowLengths = new int[rows];

        // Get the lengths of each row
        for (int row = 0; row < rows; row++) {
            //System.out.println("row length: " + Integer.toString(maze[row].length) + " " + Arrays.toString(maze[row]));
            rowLengths[row] = maze[row].length;
        }

        // If the detected row number != the given row number
        // then throw MazeSizeMissmatchException()
        if (rows != mazeDim[0]) {
            System.out.println("Row number does not match given dimension: row num = " + rows
                    + " mazeDim[row] = " + mazeDim[0]);
            throw new MazeSizeMissmatchException();
        }

        // For each length in rowLengths, if the length is different to any other length in rowLengths
        // then throw MazeMalformedException()
        for (int length : rowLengths) {
            for (int rowLength : rowLengths) {
                if (length != rowLength) {
                    System.out.println("Inconsistent columns: c1 = " + length
                            + " c2 = " + rowLength);
                    throw new MazeMalformedException();
                }
            }
        }

        // Check if maze has correct walls
        if (!has_correct_walls(rows, columns, maze)) {
            throw new MazeMalformedException();
        }

        // Check if there is a start and end
        if (!has_start_or_end(rows, columns, maze)) {
            throw new MazeMalformedException();
        }
    }

    private static boolean has_correct_walls(int rows, int columns, char[][] maze) {
        // Check the first and last row of maze to detect all characters are '#'
        System.out.println("Check the first and last row of maze to detect all characters are '#'");
        System.out.flush();
        for (int i = 0; i < columns; i++) {
            if (maze[0][i] != '#' || maze[rows - 1][i] != '#') {
                System.out.println("Incorrect top or bottom row");
                return false;
            }
        }

        // Check the walls of the maze are '#"
        for (int row = 1; row < rows - 1; row++) {
            if (maze[row][0] != '#' || maze[row][columns - 1] != '#') {
                System.out.println("Missing # in left of right wall");
                return false;
            }
        }
        return true;
    }

    private static boolean has_start_or_end(int rows, int columns, char[][] maze) {
        // Check if there is a start and end
        System.out.println("Check if there is a start and end");
        System.out.flush();
        boolean start = false;
        boolean end = false;
        for (int row = 1; row < rows - 1; row++) {
            for (int column = 1; column < columns - 1; column++) {
                //System.out.println(maze[row][column]);
                if (maze[row][column] == 'S') {
                    start = true;
                }
                if (maze[row][column] == 'E') {
                    end = true;
                }
            }
        }
        if (!start) {
            System.out.println("Maze has no start tile");
            return false;
        }
        if (!end) {
            System.out.println(("Maze has no end tile"));
            return false;
        }
        return true;
    }
}
