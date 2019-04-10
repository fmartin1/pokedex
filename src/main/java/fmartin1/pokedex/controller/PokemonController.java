package fmartin1.pokedex.controller;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.model.pokemon.generation.Generation;
import fmartin1.pokedex.model.pokemon.generation.Generations;
import fmartin1.pokedex.repository.PokemonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("pokemon")
public class PokemonController {
    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    private final PokemonRepository pokemonRepository;

    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void post(@RequestBody @Valid Pokemon pokemon) {
        logger.info("Posted Pokemon {}", pokemon.getName());
        if(pokemon.getGeneration() == Generation.GENERATION_0) {
            throw new IllegalArgumentException("Generation 0 is not valid.");
        }
        pokemonRepository.save(pokemon);
    }

    @GetMapping("{nameOrId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Pokemon getByNameOrId(@PathVariable("nameOrId") String nameOrId) {
        logger.info("Get {}", nameOrId);
        if(nameOrId.matches("\\d+")) {
            return pokemonRepository.findById(Integer.parseInt(nameOrId));
        } else {
            return pokemonRepository.findByName(nameOrId.toLowerCase());
        }
    }

    @GetMapping("type/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Pokemon> getByType(@PathVariable("name") String name) {
        logger.info("Get type {}", name);
        return pokemonRepository.findByType1OrType2(name, name);
    }

    @GetMapping("generation/{id}")
    @ResponseBody
    public List<Pokemon> getByGeneration(@PathVariable("id") int id) {
        logger.info("Get generation {}", id);
        return pokemonRepository.findByGeneration(Generations.getGenerationById(id));
    }
}
