package complete;

public class Cell {
    int X;
    int Y;
    Cell previous = null;
    boolean isJunction;
    boolean isStartCell;
    boolean isEndCell;

    public Cell(int x, int y, Cell previous) {
        X = x;
        Y = y;
        this.previous = previous;
    }

    public Cell(int x, int y) {
        X = x;
        Y = y;
    }

    public Cell getPrevious() {
        return previous;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setJunction(boolean junction) {
        isJunction = junction;
    }

    public void setStartCell(boolean startCell) {
        isStartCell = startCell;
    }

    public void setEndCell(boolean endCell) {
        isEndCell = endCell;
    }
}
