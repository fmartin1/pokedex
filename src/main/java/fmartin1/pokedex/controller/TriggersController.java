package fmartin1.pokedex.controller;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.repository.PokemonRepository;
import fmartin1.pokedex.service.PokeDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;


@RestController
@RequestMapping("triggers")
public class TriggersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriggersController.class);

    private final PokemonRepository pokemonRepository;

    private final PokeDataService pokeDataService;

    public TriggersController(PokemonRepository pokemonRepository, PokeDataService pokeDataService) {
        this.pokemonRepository = pokemonRepository;
        this.pokeDataService = pokeDataService;
    }

    @PostMapping("dbRefresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void triggerDbRefresh() {
        LOGGER.info("dbRefresh requested");
        LinkedHashMap<String, Pokemon> pokemonMap = pokeDataService.getPokemonFromPokeAPI();
        pokemonRepository.save(pokemonMap.values());
    }
}
