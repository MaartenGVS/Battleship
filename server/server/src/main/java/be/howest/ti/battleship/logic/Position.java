package be.howest.ti.battleship.logic;

import com.fasterxml.jackson.annotation.JsonCreator;


public class Position {

    private  int row;
    private  int column;



    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @JsonCreator
    public Position(String txt) {
        String[] parts = txt.split(String.valueOf('-'));
        row = parts[0].charAt(0) - 'A' + 1; // Begin from 1 instead of 0
        column = Integer.parseInt(parts[1]);
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int retrieveRow() {
        return row;
    }

    public int retrieveCol() {
        return column;
    }


    //ToString, equals and HashCode
    @Override
    public String toString() {
        return Character.toString(64 + row) + "-" + column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (row != position.row) return false;
        return column == position.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }
}
