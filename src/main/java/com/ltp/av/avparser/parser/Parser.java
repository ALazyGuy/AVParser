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

@Component
public class Parser {

    private final String PRODUCERS_URL = "https://av.by/";

    private static Logger log = LoggerFactory.getLogger(Parser.class);

    private List<Car> loadCars(String carsUrl, Model m) {
        List<Car> result = new ArrayList<>();
        try {
            Document document = Jsoup.connect(carsUrl).get();
            Elements articles = document.select(".listing-item-main");

            for (Element article : articles) {
                Car c = new Car();
                c.setModel(m);
                c.setComments(article.getElementsByClass("listing-item-message-in").text());
                if (c.getComments().length() >= 255) c.setComments(c.getComments().substring(0, 252) + "...");
                String[] arr = article.parent().getElementsByClass("listing-item-desc").text().split(" ");
                c.setMileage((int) (Double.parseDouble(arr[arr.length - 2]) * (arr[arr.length - 1].equals("км") ? 1 : 1.609344)));
                Elements itemPrice = article.parent().getElementsByClass("listing-item-price");
                c.setCost(Integer.parseInt(itemPrice.text().substring(5, itemPrice.text().indexOf('р')).replaceAll("\\s+", "")));
                try {
                    c.setReleased((new SimpleDateFormat("MMMM d,yyyy", Locale.ENGLISH)).
                            parse("January 01," + itemPrice.text().substring(0, 4)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                result.add(c);
            }

        } catch (IOException e) {
            log.error("Error: Can't read data from source " + carsUrl);
        }
        return result;
    }

    private List<Model> loadModels(String modelUrl, Producer p) {
        List<Model> result = new ArrayList<>();
        try {
            Document document = Jsoup.connect(modelUrl).get();
            Map<String, String> cars = new HashMap<>();
            document.select(".brandsitem--primary a span").forEach((element -> cars.put(element.text(), element.parent().attr("href"))));
            for(String name : cars.keySet()){
                Model m = new Model();
                m.setName(name);
                m.setCars(loadCars(cars.get(name), m));
                m.setProducerID(p);
                result.add(m);
            }
        } catch (IOException e) {
            log.error("Error: Can't read data from source " + modelUrl);
        }
        return result;
    }

    public List<Producer> loadProducers() {
        List<Producer> result = new ArrayList<>();
        try {
            Document document = Jsoup.connect(PRODUCERS_URL).get();
            Map<String, String> models = new HashMap<>();
            document.select(".brandsitem--primary a span").forEach((element -> models.put(element.text(), element.parent().attr("href"))));
            for(String name : models.keySet()){
                Producer p = new Producer();
                p.setName(name);
                p.setModels(loadModels(models.get(name), p));
                result.add(p);
            }
        } catch (IOException e) {
            log.error("Error: Can't read data from source " + PRODUCERS_URL);
        }
        return result;
    }
}
