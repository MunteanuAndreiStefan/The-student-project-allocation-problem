package Model;

public abstract class Person {
    protected String email;
    protected String name;

    public abstract boolean isFree();

    public abstract int getHeaderLength(String header);

    public Person(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}