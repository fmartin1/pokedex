package fmartin1.pokedex.controller;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.service.PokedexService;
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

    private final PokedexService pokedexService;

    public PokemonController(PokedexService pokedexService) {
        this.pokedexService = pokedexService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void post(@RequestBody @Valid Pokemon pokemon) {
        logger.info("Posted Pokemon {}", pokemon.getName());
        pokedexService.postPokemon(pokemon);
    }

    @GetMapping("{nameOrId}")
    @ResponseBody
    public Pokemon getByNameOrId(@PathVariable("nameOrId") String nameOrId) {
        logger.info("Get {}", nameOrId);
        return pokedexService.findPokemonByNameOrId(nameOrId);
    }

    @GetMapping("type/{name}")
    @ResponseBody
    public List<Pokemon> getByType(@PathVariable("name") String name) {
        logger.info("Get type {}", name);
        return pokedexService.getPokemonByType(name);
    }

    @GetMapping("generation/{id}")
    @ResponseBody
    public List<Pokemon> getByGeneration(@PathVariable("id") int id) {
        logger.info("Get generation {}", id);
        return pokedexService.getPokemonByGeneration(id);
    }
}
