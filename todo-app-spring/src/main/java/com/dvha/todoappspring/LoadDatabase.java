package com.dvha.todoappspring;

import com.dvha.todoappspring.model.Task;
import com.dvha.todoappspring.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
class LoadDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TodoRepository todoRepository){
        return args ->{
            logger.info("Preloading " + todoRepository.save(new Task("Eat", false, new Date())));
            logger.info("Preloading " + todoRepository.save(new Task("Sleep", false, new Date())));
            logger.info("Preloading " + todoRepository.save(new Task("Repeat", false, new Date())));

        };

    }

}
