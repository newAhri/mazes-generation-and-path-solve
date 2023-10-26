package complete;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class SolveDepthSearch {

    public String[][] maze;

    public int curX = 0, curY = 0, randIndex;
    public int finishX, finishY;

    public Stack<Cell> stack = new Stack<>();
    public Stack<Cell> visited = new Stack<>();
    public ArrayList<Cell> tempNeighbours = new ArrayList<>();
    public boolean canGoLeft, canGoRight, canGoDown, canGoUp;
    Random rand = new Random();

    public SolveDepthSearch(String[][] maze) {
        this.maze = maze;
        finishX = maze[0].length - 2;
        finishY = maze.length - 2;
    }

    public void solve() {
        stack.add(new Cell(1, 1));
        visited.add(new Cell(1, 1));
        checkPath(1, 1);

        for (Cell cell : stack) {
            System.out.print("(" + cell.getX() + " " + cell.getY() + ") ");
        }
        System.out.println("\n");

        for (Cell c : stack) {
            maze[c.getY()][c.getX()] = ". ";
        }
        for (int i=0; i<maze.length; i++) {
            for (int j=0; j<maze[0].length;j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }

    }

    public void checkPath(int x, int y) {
        tempNeighbours.clear();
        canGoLeft = x - 1 > 0 && maze[y][x - 1] != "X " && !cellWasVisited(x - 1, y);
        canGoDown = y + 1 < maze.length - 1 && maze[y + 1][x] != "X " && !cellWasVisited(x, y + 1);
        canGoRight = x + 1 < maze[0].length - 1 && maze[y][x + 1] != "X " && !cellWasVisited(x + 1, y);
        canGoUp = y - 1 > 0 && maze[y - 1][x] != "X " && !cellWasVisited(x, y - 1);

        if (canGoLeft) {
            tempNeighbours.add(new Cell(x - 1, y));
        }
        if (canGoRight) {
            tempNeighbours.add(new Cell(x + 1, y));
        }
        if (canGoDown) {
            tempNeighbours.add(new Cell(x, y + 1));
        }
        if (canGoUp) {
            tempNeighbours.add(new Cell(x, y - 1));
        }

        if (!tempNeighbours.isEmpty()) {
            randIndex = rand.nextInt(tempNeighbours.size());
            curX = tempNeighbours.get(randIndex).getX();
            curY = tempNeighbours.get(randIndex).getY();
            stack.add(new Cell(curX, curY));
            visited.add(new Cell(curX, curY));
            move(curX, curY);
        } else {
            stack.pop();
            checkPath(stack.lastElement().getX(), stack.lastElement().getY());
        }

    }

    public boolean cellWasVisited(int x, int y) {
        for (Cell c : visited) {
            if (c.getX() == x && c.getY() == y)
                return true;
        }
        return false;
    }

    public void move(int x, int y) {
        if (x == finishX && y == finishY) {
            return;
        } else {
            checkPath(x, y);
        }
    }
}


