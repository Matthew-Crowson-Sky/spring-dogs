package com.sky.springdogs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;
    private boolean hasGarden;

    public Home(Integer id, String address, boolean hasGarden) {
        this.id = id;
        this.address = address;
        this.hasGarden = hasGarden;
    }

    public Home(String address, boolean hasGarden) {
        this.address = address;
        this.hasGarden = hasGarden;
    }

    public Home() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Home{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", hasGarden=" + hasGarden +
                '}';
    }
}
