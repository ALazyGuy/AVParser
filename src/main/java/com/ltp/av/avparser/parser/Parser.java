package com.ltp.av.avparser.parser;

import com.ltp.av.avparser.entities.Car;
import com.ltp.av.avparser.entities.Model;
import com.ltp.av.avparser.entities.Producer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//Just parsing html document from $PRODUCERS_URL
@Component
public class Parser {
    //Target site URL
    private final String PRODUCERS_URL = "https://av.by/";

    private Document document;
    private Logger log = LoggerFactory.getLogger(Parser.class);

    /*private final int MAXPRODUCERS = 1;
    private final int MAXMODELS = 1;
    private final int MAXCARS = 2;
    private int carCounter = 0;
    private int modelsCounter = 0;
    private int producersCounter = 0;*/

    private List<Car> loadCars(String carsUrl, Model m){
        List<Car> result = new ArrayList<>();
        try {
            document = Jsoup.connect(carsUrl).get();
            Elements articles = document.select(".listing-item-main");

            articles.forEach((article) -> {
                //if(carCounter == MAXCARS) return;
                Car c = new Car();
                c.setModel(m);
                c.setComments(article.getElementsByClass("listing-item-message-in").text());
                if(c.getComments().length() >= 255) c.setComments(c.getComments().substring(0, 252) + "...");
                String[] arr = article.parent().getElementsByClass("listing-item-desc").text().split(" ");
                c.setMileage((int)(Double.parseDouble(arr[arr.length - 2]) * (arr[arr.length - 1].equals("км") ? 1 : 1.609344)));
                c.setCost(Integer.parseInt(article.parent().getElementsByClass("listing-item-price")
                        .text().substring(5, article.parent().getElementsByClass("listing-item-price").text()
                        .indexOf('р')).replaceAll("\\s+", "")));
                try {
                    c.setReleased((new SimpleDateFormat("MMMM d,yyyy", Locale.ENGLISH)).
                    parse("January 01," + article.parent().getElementsByClass("listing-item-price").text().substring(0, 4)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                result.add(c);
                //carCounter++;
            });
        }catch(IOException e){log.error("Error: Can't read data from source " + carsUrl);}
        return result;
    }

    private List<Model> loadModels(String modelUrl, Producer p){
        List<Model> result = new ArrayList<>();
        try {
            document = Jsoup.connect(modelUrl).get();
            Map<String, String> cars = new HashMap<>();
            document.select(".brandsitem--primary a span").forEach((element -> cars.put(element.text(), element.parent().attr("href"))));
            cars.forEach((name, url) -> {
                //if(modelsCounter == MAXMODELS) return;
                Model m = new Model();
                m.setName(name);
                //carCounter = 0;
                m.setCars(loadCars(url, m));
                m.setProducerID(p);
                result.add(m);
                //modelsCounter++;
            });
        }catch(IOException e){log.error("Error: Can't read data from source " + modelUrl);}
        return result;
    }

    public List<Producer> loadProducers(){
        List<Producer> result = new ArrayList<>();
        try {
            document = Jsoup.connect(PRODUCERS_URL).get();
            Map<String, String> models = new HashMap<>();
            document.select(".brandsitem--primary a span").forEach((element -> models.put(element.text(), element.parent().attr("href"))));
            models.forEach((name, url) -> {
                //if(producersCounter >= MAXPRODUCERS) return;
                Producer p = new Producer();
                p.setName(name);
                //modelsCounter = 0;
                p.setModels(loadModels(url, p));
                result.add(p);
                //producersCounter++;
            });
        }catch(IOException e){log.error("Error: Can't read data from source " + PRODUCERS_URL);}
        return result;
    }
}
