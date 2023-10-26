package complete;


import java.util.ArrayList;
import java.util.Random;

public class MazeGenPrim {

    public String[][] maze;

    boolean[][] visitedOrWall;
    ArrayList<Cell> frontierCells = new ArrayList<>();
    ArrayList<Cell> myVisitedNeighbours = new ArrayList<>();

    Random rand = new Random();
    Cell curCell;
    int wallX, wallY, randInd;

    public MazeGenPrim(int rows, int cols) {
        maze = new String[rows][cols];
    }

    public String[][] generateMaze() {
        genWallsForMaze();
        visitedOrWall = new boolean[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int r = 0; r < maze[0].length; r++) {
                if (maze[i][r] == "X ")
                    visitedOrWall[i][r] = true;
                else
                    visitedOrWall[i][r] = false;
            }
        }

        int startX = 2 * rand.nextInt(((maze[0].length - 3) / 2) + 1) + 1;
        int startY = 2 * rand.nextInt(((maze.length - 3) / 2) + 1) + 1;


        curCell = new Cell(startX, startY);
        visitedOrWall[startY][startX] = true;
        action(curCell.getX(), curCell.getY());

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

    public boolean noInclude(int x, int y) {
        for (Cell c : frontierCells) {
            if (c.getX() == x && c.getY() == y)
                return false;
        }
        return true;
    }

    public void adjacentSearch(int x, int y) {

        if (x - 2 > 0 && isNotVisited(x - 2, y) && noInclude(x - 2, y)) {
            frontierCells.add(new Cell(x - 2, y));

        }

        if (x + 2 < maze[0].length - 1 && isNotVisited(x + 2, y) && noInclude(x + 2, y)) {
            frontierCells.add(new Cell(x + 2, y));

        }

        if (y - 2 > 0 && isNotVisited(x, y - 2) && noInclude(x, y - 2)) {
            frontierCells.add(new Cell(x, y - 2));

        }

        if (y + 2 < maze.length - 1 && isNotVisited(x, y + 2) && noInclude(x, y + 2)) {
            frontierCells.add(new Cell(x, y + 2));

        }

    }

    public void findWhiteNeighbours(int x, int y) {
        if (x - 2 > 0 && !isNotVisited(x - 2, y)) {
            myVisitedNeighbours.add(new Cell(x - 2, y));

        }

        if (x + 2 < maze[0].length - 1 && !isNotVisited(x + 2, y)) {
            myVisitedNeighbours.add(new Cell(x + 2, y));

        }

        if (y - 2 > 0 && !isNotVisited(x, y - 2)) {
            myVisitedNeighbours.add(new Cell(x, y - 2));

        }

        if (y + 2 < maze.length - 1 && !isNotVisited(x, y + 2)) {
            myVisitedNeighbours.add(new Cell(x, y + 2));

        }
    }

    public boolean isNotVisited(int x, int y) {

        if (visitedOrWall[y][x] == false)
            return true;
        return false;
    }

    public void action(int x, int y) {
        adjacentSearch(x, y);
        while (frontierCells.size() > 0) {

            randInd = rand.nextInt(frontierCells.size());
            curCell = frontierCells.get(randInd);
            frontierCells.remove(randInd);

            visitedOrWall[curCell.getY()][curCell.getX()] = true;

            findWhiteNeighbours(curCell.getX(), curCell.getY());
            randInd = rand.nextInt(myVisitedNeighbours.size());
            removeWall(curCell, myVisitedNeighbours.get(randInd));

            myVisitedNeighbours.clear();

            action(curCell.getX(), curCell.getY());
            curCell = null;
        }

    }

    public void removeWall(Cell c1, Cell c2) {
        wallX = (c1.getX() + c2.getX()) / 2;
        wallY = (c1.getY() + c2.getY()) / 2;
        maze[wallY][wallX] = "  ";
    }
}
