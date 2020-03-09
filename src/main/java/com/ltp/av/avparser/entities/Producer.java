package com.ltp.av.avparser.entities;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="producers")
public class Producer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long ID;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Model> models = new ArrayList<>();

    private String name;

    public void addModel(Model m){
        models.add(m);
    }

    public void setModels(List<Model> models){
        this.models = models;
    }

    public List<Model> getModels(){
        return models;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Long getID(){
        return ID;
    }

    public void setID(Long ID){
        this.ID = ID;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        if(models == null) buffer.append("Empty");
        else {
            getModels().forEach((model) -> buffer.append(model.toString() + ",\n\t"));
            buffer.setLength(buffer.length() - 3);
        }
        return String.format("Producer[ID: %d, Name: %s -> (\n\t%s\n)]", getID(), getName(), buffer.toString());
    }
}
