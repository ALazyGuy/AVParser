package com.ltp.av.avparser.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="cars")
public class Car {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long ID;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "modelID", nullable = false)
    private Model model;

    private String comments;
    private Date released;
    private int mileage;
    private int cost;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getReleased() {
        return released;
    }

    public void setReleased(Date releaseDate) {
        this.released = releaseDate;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("Car[ID: %d, Comments: %s, Release: %s, Mileage: %d, Cost: %d]", getID(), getComments(), getReleased().toString(), getMileage(), getCost());
    }
}
