package eizougraphic.sintret.finsrecipe.sql;

import java.util.ArrayList;
import java.util.List;

public class Person {
    public String name;
    public String address;


    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Person(){

        initializeData();
    }


    private List<Person> persons;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    void initializeData() {
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));
    }
}