package be.howest.ti.battleship.info;

public class Author {

    private final String name;

    private static final String STATUS = "Student";


    //Constructor
    public Author(String name) {
        this.name = name;
    }

    //Getter
    public String getName() {
        return name;
    }

    //Setter
    public String getStatus() {
        return STATUS;
    }



    //toString

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}
