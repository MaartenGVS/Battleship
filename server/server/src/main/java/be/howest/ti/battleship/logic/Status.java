package be.howest.ti.battleship.logic;

public enum Status {
    STARTED("started"),
    WAITING("waiting");


    private final String state;

    Status(String state) {
        this.state = state;
    }

    public String getStatusInLowerCase() {
        return state;
    }
}
