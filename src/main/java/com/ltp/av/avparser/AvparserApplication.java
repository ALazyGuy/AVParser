package com.ltp.av.avparser;

import com.ltp.av.avparser.entities.Model;
import com.ltp.av.avparser.entities.Producer;
import com.ltp.av.avparser.parser.Parser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class AvparserApplication {

    public static void main(String[] args) throws IOException {
        Parser p = new Parser();
        List<Producer> producers = p.loadProducers();
        producers.forEach((pr) -> System.out.println(pr.toString()));
        //SpringApplication.run(AvparserApplication.class, args);
    }

}
