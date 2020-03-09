package com.ltp.av.avparser.controllers;

import com.ltp.av.avparser.entities.Producer;
import com.ltp.av.avparser.parser.Parser;
import com.ltp.av.avparser.repositories.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ShowController {

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private Parser parser;

    @GetMapping("/show")
    public String test(@RequestParam String type) throws IOException {
        if (type.toLowerCase().trim().equals("all")) {
            StringBuilder buffer = new StringBuilder();
            for (Producer p : producerRepository.findAll()) {
                buffer.append(p.toString() + "<br>");
            }
            return buffer.toString();
        }
        return "<b>Nothing found!</b>";
    }

    @GetMapping("/add")
    public String addEntity() {
        parser.loadProducers().forEach((producer -> producerRepository.save(producer)));
        return "Done";
    }

}
