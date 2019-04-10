package fmartin1.pokedex.service;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.repository.PokemonRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class PokeRepoService {

    private static final Logger LOGGER = LoggerFactory.getLogger("PokeRepo");

    private final PokemonRepository pokemonRepository;

    private final PokeAPIClient pokeAPIClient;

    public PokeRepoService(PokemonRepository pokemonRepository, PokeAPIClient pokeAPIClient) {
        this.pokemonRepository = pokemonRepository;
        this.pokeAPIClient = pokeAPIClient;
    }

    public void refreshPokemon(String name) {
        Optional<Pokemon> pokemon = pokeAPIClient.getPokemon(name);
        if(pokemon.isPresent()) {
            pokemonRepository.save(pokemon.get());
            LOGGER.info("Pokemon updated");
        } else {
            LOGGER.info("Not found in PokeAPI");
        }
    }

    public void refreshDB() throws NotFoundException {
        LinkedHashMap<String, Pokemon> pokemonMap = pokeAPIClient.getPokemon();
        if(pokemonMap.isEmpty()) {
            throw new NotFoundException("Pokemon list could not be retrieved from PokeAPI");
        }
        pokemonRepository.save(pokemonMap.values());
    }
}
