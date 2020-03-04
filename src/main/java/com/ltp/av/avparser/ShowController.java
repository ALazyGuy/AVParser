package com.ltp.av.avparser;

import com.ltp.av.avparser.entities.Producer;
import com.ltp.av.avparser.entities.ProducerRepository;
import com.ltp.av.avparser.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ShowController {

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    ApplicationContext applicationContext;

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

    @GetMapping("/add")
    public @ResponseBody String addEntity(){
        applicationContext.getBean(Parser.class).loadProducers().forEach((producer -> producerRepository.save(producer)));
        return "Done";
    }

}
