package com.ltp.av.avparser.repositories;

import com.ltp.av.avparser.entities.Producer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProducerRepository extends CrudRepository<Producer, Long> {
    List<Producer> findByName(String name);
}
