package com.sky.springdogs.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
// @Table used to set the name of table you create
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Showing that you can specify some attributes as unique and nullable if you wanted to
    @Column (unique = false, nullable = true)
    @NotEmpty @Size(min = 1, max = 50)
    private String name;

    @NotEmpty @Size(min = 1, max = 50)
    private String breed;

    @NotNull @Range(min = 0, max = 36)
    private Integer age;

    public Dog(Integer id, String name, String breed, Integer age) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    public Dog(String name, String breed, Integer age) {
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    public Dog(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                '}';
    }
}
