package fmartin1.pokedex.controller;

import com.sun.deploy.net.HttpResponse;
import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.repository.PokemonRepository;
import fmartin1.pokedex.service.PokeAPIClient;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Optional;


@RestController
@RequestMapping("refresh")
public class TriggersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriggersController.class);

    private final PokemonRepository pokemonRepository;

    private final PokeAPIClient pokeAPIClient;

    public TriggersController(PokemonRepository pokemonRepository, PokeAPIClient pokeAPIClient) {
        this.pokemonRepository = pokemonRepository;
        this.pokeAPIClient = pokeAPIClient;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void triggerDbRefresh() throws NotFoundException {
        LOGGER.info("dbRefresh requested");
        LinkedHashMap<String, Pokemon> pokemonMap = pokeAPIClient.getPokemon();
        if(pokemonMap.isEmpty()) {
            throw new NotFoundException("Pokemon list could not be retrieved from PokeAPI.");
        }
        pokemonRepository.save(pokemonMap.values());
    }

    @PostMapping("/{pokemonName}")
    public ResponseEntity refreshPokemon(@PathVariable String pokemonName) {
        HttpStatus status = HttpStatus.OK;

        LOGGER.info("Refresh {}", pokemonName);

        Optional<Pokemon> pokemon = pokeAPIClient.getPokemon(pokemonName);
        if(pokemon.isPresent()) {
            pokemonRepository.save(pokemon.get());
        } else {
            LOGGER.info("Not found in PokeAPI.");
            status = HttpStatus.NOT_MODIFIED;
        }
        ResponseEntity responseEntity = new ResponseEntity(status);
        return new ResponseEntity(status);
    }
}
