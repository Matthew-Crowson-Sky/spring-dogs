package com.sky.springdogs.domain;

public class Dog {
    private String name;
    private String breed;
    private Integer age;

    public Dog(String name, String breed, Integer age) {
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    public Dog(){
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
