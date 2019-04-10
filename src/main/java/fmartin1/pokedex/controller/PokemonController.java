package fmartin1.pokedex.controller;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.repository.PokemonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pokemon")
public class PokemonController {
    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    private final PokemonRepository pokemonRepository;

    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postPokemon(@RequestBody Pokemon pokemon) {
        logger.info("Posted Pokemon {}", pokemon.getName());
        pokemonRepository.save(pokemon);
    }
}
