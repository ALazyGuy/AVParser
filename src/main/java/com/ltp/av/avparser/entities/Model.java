package com.ltp.av.avparser.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="models")
public class Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long ID;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "producerID", nullable = false)
    private Producer producerID;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();

    public void addCar(Car car){
        cars.add(car);
    }

    public Producer getProducerID() {
        return producerID;
    }

    public void setProducerID(Producer producerID) {
        this.producerID = producerID;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void setID(Long ID){
        this.ID = ID;
    }

    public Long getID(){
        return ID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        if(cars == null) buffer.append("Empty");
        else {
            getCars().forEach((car) -> buffer.append(car.toString() + ",\n\t\t"));
            buffer.setLength(buffer.length() - 4);
        }
        return String.format("Model[ID: %d, Name: %s -> (\n\t\t%s\n)]", getID(), getName(), buffer.toString());
    }
}