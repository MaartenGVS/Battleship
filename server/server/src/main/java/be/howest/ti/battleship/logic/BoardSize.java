package be.howest.ti.battleship.logic;

public class BoardSize {

    public static final int MIN_SIZE = 5;
    public static final int MAX_SIZE = 50;


    private int rows;
    private int cols;


    //Constructor
    public BoardSize(int rows, int cols) {
        validateBoardSize(rows, cols);
        this.rows = rows;
        this.cols = cols;
    }


    //Getters
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }


    //Setters
    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }


    //Logic
    private void validateBoardSize(int rows, int cols) {
        if (rows < MIN_SIZE || rows > MAX_SIZE || cols < MIN_SIZE || cols > MAX_SIZE) {
            throw new IllegalArgumentException("Invalid board size, please choose a size between 5 and 50");
        }
    }

    public boolean isInside(int row, int col){
        return (row <= getRows() && col <= getCols()) && (row > 0 && col > 0);
    }


    //toString, Equals and hashCode
    @Override
    public String toString() {
        return "BoardSize{" +
                "rows=" + rows +
                ", cols=" + cols +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardSize boardSize = (BoardSize) o;

        if (rows != boardSize.rows) return false;
        return cols == boardSize.cols;
    }

    @Override
    public int hashCode() {
        int result = rows;
        result = 31 * result + cols;
        return result;
    }
}
