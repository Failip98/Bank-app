package app.Entities;


import app.annotations.Column;

public class User {
    @Column
    private String person_id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String password;


    public String getName() {
        return name;
    }

    public String getPerson_id() {
        return person_id;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString(){
        return String.format("User: { person_id: %s, name: %s, surname: %s, password: %s }", person_id, name, surname, password);
    }
}
