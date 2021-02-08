package fmartin1.pokedex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PokedexMain {
    public static void main(String[] args) {
        SpringApplication.run(PokedexMain.class, args);
    }
}
