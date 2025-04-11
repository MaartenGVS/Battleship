package be.howest.ti.battleship.logic;

public enum GamePiece {

    CARRIER( 5),
    BATTLESHIP(4),
    CRUISER(3),
    SUBMARINE(3),
    DESTROYER( 2);

    private final int size;


    GamePiece(int size) {
        this.size = size;
    }


    public int getSize() {
        return size;
    }
}
