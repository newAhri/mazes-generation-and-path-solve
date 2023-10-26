package complete;

import java.util.ArrayList;
import java.util.Collections;

public class SolveDeathEnd {
    public Cell startCell = new Cell(1, 1);
    public String[][] maze;
    public boolean[][] blockedCells;
    public ArrayList<Cell> deathEnds = new ArrayList<>();
    public ArrayList<Boolean> isOneWayOut;
    public ArrayList<Cell> path = new ArrayList<>();

    public SolveDeathEnd(String[][] maze) {
        this.maze = maze;
        blockedCells = new boolean[maze.length][maze[0].length];
    }

    public void solve() {
        findDeathEnds();
        //close all death ends
        for (int i = 0; i < deathEnds.size(); i++) {
            blockCells(deathEnds.get(i));
        }
        findPath(startCell);
    }

    public void findDeathEnds() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                //pass if node is a start, end or equals 1 (wall)
                if ((i == startCell.getX() && j == startCell.getY()) || (i == maze.length - 2 && j == maze[0].length - 2) || (maze[i][j].equals("X ")))
                    continue;
                isOneWayOut = new ArrayList<>();

                //death end has only one way out
                try {
                    if (maze[i - 1][j].equals("  ")) isOneWayOut.add(true);
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                try {
                    if (maze[i + 1][j].equals("  ")) isOneWayOut.add(true);
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                try {
                    if (maze[i][j - 1].equals("  ")) isOneWayOut.add(true);
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                try {
                    if (maze[i][j + 1].equals("  ")) isOneWayOut.add(true);
                } catch (ArrayIndexOutOfBoundsException ex) {
                }

                if (isOneWayOut.size() == 1) {
                    deathEnds.add(new Cell(i, j));
                    blockedCells[i][j] = true;
                }
            }
        }
    }

    public void blockCells(Cell currentCell) {
        int curX = currentCell.getX();
        int curY = currentCell.getY();

        Cell cell = checkJunctions(currentCell);

        //not blocking main path, continue iteration
        if (cell.isJunction || cell.isEndCell || cell.isStartCell || currentCell.equals(cell)) {
            return;
        }//else cell is a nodeToGo

        blockedCells[curX][curY] = true;
        blockCells(cell);
    }

    public Cell checkJunctions(Cell cell) {
        Cell cellToGo = null;
        int directionCount = 0;
        int curX = cell.getX();
        int curY = cell.getY();

        boolean canGoRight = (curY + 1 < maze[0].length) && (maze[curX][curY + 1].equals("  ")) && (!blockedCells[curX][curY + 1]);
        boolean canGoDown = (curX + 1 < maze.length) && (maze[curX + 1][curY].equals("  ")) && (!blockedCells[curX + 1][curY]);
        boolean canGoLeft = (curY - 1 >= 0) && (maze[curX][curY - 1].equals("  ")) && (!blockedCells[curX][curY - 1]);
        boolean canGoUp = (curX - 1 >= 0) && (maze[curX - 1][curY].equals("  ")) && (!blockedCells[curX - 1][curY]);

        if (canGoRight) {
            cellToGo = new Cell(curX, curY + 1);
            directionCount++;
        }
        if (canGoDown) {
            cellToGo = new Cell(curX + 1, curY);
            directionCount++;
        }
        if (canGoLeft) {
            cellToGo = new Cell(curX, curY - 1);
            directionCount++;
        }
        if (canGoUp) {
            cellToGo = new Cell(curX - 1, curY);
            directionCount++;
        }
        if (directionCount > 1) {
            cell.setJunction(true);
            return cell;
        } else if ((cell.getX() == maze.length - 2) && (cell.getY() == maze[0].length - 2)) {
            cell.setEndCell(true);
            return cell;
        } else if ((cell.getX() == startCell.getX()) && (cell.getY() == startCell.getY())) {
            cell.setStartCell(true);
            return cell;
        } else if (directionCount == 0) {
            return cell;
        }
        //by default return nodeToGo - one way to go
        return cellToGo;
    }

    public Cell checkDirection(Cell cell) {
        if (cell.getX() == maze.length - 2 && cell.getY() == maze[0].length - 2) {
            System.out.println("Path is found!");
            createPath(cell);
            outputPath();
            System.exit(0);
        }
        Cell cellToGo = null;
        int curX = cell.getX();
        int curY = cell.getY();

        boolean canGoRight = (curY + 1 < maze[0].length) && (maze[curX][curY + 1].equals("  ")) && (!blockedCells[curX][curY + 1]);
        boolean canGoDown = (curX + 1 < maze.length) && (maze[curX + 1][curY].equals("  ")) && (!blockedCells[curX + 1][curY]);
        boolean canGoLeft = (curY - 1 >= 0) && (maze[curX][curY - 1].equals("  ")) && (!blockedCells[curX][curY - 1]);
        boolean canGoUp = (curX - 1 >= 0) && (maze[curX - 1][curY].equals("  ")) && (!blockedCells[curX - 1][curY]);

        if (canGoRight) {
            cellToGo = new Cell(curX, curY + 1, cell);
        } else if (canGoDown) {
            cellToGo = new Cell(curX + 1, curY, cell);
        } else if (canGoLeft) {
            cellToGo = new Cell(curX, curY - 1, cell);
        } else if (canGoUp) {
            cellToGo = new Cell(curX - 1, curY, cell);
        } else {
            System.out.println("There is no path!");
            System.exit(0);
        }
        return cellToGo;
    }

    public void findPath(Cell cell) {
        blockedCells[cell.getX()][cell.getY()] = true;
        Cell cellToGo = checkDirection(cell);
        findPath(cellToGo);
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
