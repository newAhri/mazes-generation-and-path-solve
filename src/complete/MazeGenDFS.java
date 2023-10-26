package complete;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeGenDFS {

    String[][] maze;
    int cellCount;
    public ArrayList<Cell> visited = new ArrayList<>();
    public ArrayList<Cell> tempNeighbours = new ArrayList<>();
    public ArrayList<Cell> tempWalls = new ArrayList<>();

    public Stack<Cell> stack = new Stack<>();
    public Random rand = new Random();

    public MazeGenDFS(int rows, int cols) {
        maze = new String[rows][cols];
    }

    public String[][] generateMaze() {

        cellCount = (maze.length / 2) * (maze[0].length / 2);
        genWallsForMaze();

        int startX = maze[0].length - 2;
        int startY = maze.length - 2;

        stack.add(new Cell(startX, startY));
        visited.add(new Cell(startX, startY));
        findNeighbours(startX, startY);

        System.out.println();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        return maze;
    }

    public void genWallsForMaze() {

        for (int row = 0; row < maze.length; row++) {
            if (row % 2 == 0) {
                for (int col = 0; col < maze[0].length; col++) {
                    maze[row][col] = "X ";
                }
            } else {
                for (int col = 0; col < maze[0].length; col++) {
                    maze[row][col] = "  ";
                }
            }
        }
        for (int col = 0; col < maze[0].length; col++) {
            if (col % 2 == 0) {
                for (int row = 0; row < maze.length; row++) {
                    maze[row][col] = "X ";
                }
            }
        }
    }

    public boolean wasVisited(int x, int y) {
        for (Cell c : visited) {
            if (c.getX() == x && c.getY() == y)
                return true;
        }
        return false;
    }

    public void removeWall(Cell c) {
        maze[c.getY()][c.getX()] = "  ";
    }

    public void findNeighbours(int x, int y) {
        tempNeighbours.clear();
        tempWalls.clear();

        if (visited.size() == cellCount)
            return;

        if (x + 2 < maze[0].length - 1 && !wasVisited(x + 2, y)) {
            tempNeighbours.add(new Cell(x + 2, y));
            tempWalls.add(new Cell(x + 1, y));
        }

        if (x - 2 > 0 && !wasVisited(x - 2, y)) {
            tempNeighbours.add(new Cell(x - 2, y));
            tempWalls.add(new Cell(x - 1, y));
        }
        if (y + 2 < maze.length - 1 && !wasVisited(x, y + 2)) {
            tempNeighbours.add(new Cell(x, y + 2));
            tempWalls.add(new Cell(x, y + 1));
        }
        if (y - 2 > 0 && !wasVisited(x, y - 2)) {
            tempNeighbours.add(new Cell(x, y - 2));
            tempWalls.add(new Cell(x, y - 1));
        }

        int randIndex;
        if (!tempNeighbours.isEmpty()) {
            randIndex = rand.nextInt(tempNeighbours.size());
            int newX, newY;
            newX = tempNeighbours.get(randIndex).getX();
            newY = tempNeighbours.get(randIndex).getY();

            removeWall(tempWalls.get(randIndex));
            stack.add(new Cell(newX, newY));


            visited.add(new Cell(newX, newY));
            findNeighbours(stack.lastElement().getX(), stack.lastElement().getY());

        } else if (!stack.isEmpty()) {
            stack.pop();
            findNeighbours(stack.lastElement().getX(), stack.lastElement().getY());
        }

    }
}
