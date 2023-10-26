package complete;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class SolveWidthSearch {
    public Cell startCell = new Cell(1, 1);
    public String[][] maze;
    public boolean[][] visitedOrWall;
    public ArrayDeque<Cell> aboutToVisit = new ArrayDeque<>();
    public ArrayList<Cell> path = new ArrayList<>();

    public SolveWidthSearch(String[][] maze) {
        this.maze = maze;
        visitedOrWall = new boolean[maze.length][maze[0].length];
    }

    public void solve() {
        checkForWalls();
        findPath(startCell);
    }

    public void checkForWalls() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].equals("X ")) {
                    visitedOrWall[i][j] = true;
                }
            }
        }
    }

    public void findPath(Cell cell) {
        visitedOrWall[cell.getX()][cell.getY()] = true;
        if (cell.getX() == maze.length - 2 && cell.getY() == maze[0].length - 2) {
            System.out.println("Path is found!");
            createPath(cell);
            outputPath();
            System.exit(0);
        }
        checkDirections(cell);
        move();
    }

    public void checkDirections(Cell cell) {
        int curX = cell.getX();
        int curY = cell.getY();

        boolean canGoRight = (curY + 1 < maze[0].length) && (!visitedOrWall[curX][curY + 1]);
        boolean canGoDown = (curX + 1 < maze.length) && (!visitedOrWall[curX + 1][curY]);
        boolean canGoLeft = (curY - 1 >= 0) && (!visitedOrWall[curX][curY - 1]);
        boolean canGoUp = (curX - 1 >= 0) && (!visitedOrWall[curX - 1][curY]);


        if (canGoRight) {
            aboutToVisit.add(new Cell(curX, curY + 1, cell));
        }
        if (canGoDown) {
            aboutToVisit.add(new Cell(curX + 1, curY, cell));
        }
        if (canGoLeft) {
            aboutToVisit.add(new Cell(curX, curY - 1, cell));
        }
        if (canGoUp) {
            aboutToVisit.add(new Cell(curX - 1, curY, cell));
        }
    }

    public void move() {
        if (aboutToVisit.isEmpty()) {
            System.out.println("There is no path!");
            System.exit(0);
        }
        Cell stepOn = aboutToVisit.pollFirst();

        findPath(stepOn);
    }

    public void createPath(Cell cell) {
        if (cell.previous != null) {
            path.add(cell);
            createPath(cell.getPrevious());
        } else {
            path.add(startCell);
        }
    }

    public void outputPath() {
        Collections.reverse(path);
        for (Cell cell : path) {
            System.out.print("(" + cell.getX() + " " + cell.getY() + ") ");
        }
        System.out.println("\n");
        for (int i = 0; i < maze.length; i++) {
            loop:
            for (int j = 0; j < maze[0].length; j++) {
                for (Cell pathCell : path) {
                    int x = pathCell.getX();
                    int y = pathCell.getY();
                    if (i == x && j == y) {
                        System.out.print(". ");
                        continue loop;
                    }
                }
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
}