package com.ltp.av.avparser;

import com.ltp.av.avparser.entities.Car;
import com.ltp.av.avparser.entities.Model;
import com.ltp.av.avparser.entities.Producer;
import com.ltp.av.avparser.entities.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@Controller
public class ShowController {

    @Autowired
    private ProducerRepository producerRepository;

    @GetMapping("/show")
    public @ResponseBody String test(@RequestParam String type) throws IOException {
        if(type.toLowerCase().trim().equals("all")) {
            StringBuilder buffer = new StringBuilder();
            for (Producer p : producerRepository.findAll()) {
                buffer.append(p.toString() + "<br>");
            }
            return buffer.toString();
        }
        return "<b>Nothing found!</b>";
    }

    @PostMapping("/add")
    public @ResponseBody String addEntity(@RequestParam String name){
        /*if(producerRepository.findByName(name).size() != 0) return "ERROR! Producer is already exists!<br>";
        Producer producer = new Producer();
        producer.setName(name);
        producerRepository.save(producer);*/
        Car c = new Car();
        c.setCost(1200);
        c.setMileage(10);
        c.setReleased(new Date());
        c.setComments("QWE");
        Model m = new Model();
        m.addCar(c);
        m.setName("5000");
        c.setModel(m);
        Producer p = new Producer();
        p.setName("Audi");
        p.addModel(m);
        m.setProducerID(p);
        producerRepository.save(p);
        return "Saved: " + name + "<br>";
    }

}
