package fmartin1.pokedex.service;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.model.pokemon.generation.Generation;
import fmartin1.pokedex.model.pokemon.generation.Generations;
import fmartin1.pokedex.repository.PokemonRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokedexService {

    private PokemonRepository pokemonRepository;

    public PokedexService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @CachePut("pokemon")
    public void postPokemon(Pokemon pokemon) {
        if (pokemon.getGeneration() == Generation.GENERATION_0) {
            throw new IllegalArgumentException("Generation 0 is not valid.");
        }
        pokemonRepository.save(pokemon);
    }

    @Cacheable("pokemon")
    public Pokemon findPokemonByNameOrId(String nameOrId) {
        Pokemon pokemon;
        if (nameOrId.matches("\\d+")) {
            pokemon = pokemonRepository.findById(Integer.parseInt(nameOrId));
        } else {
            pokemon = pokemonRepository.findByName(nameOrId.toLowerCase());
        }
        return pokemon;
    }

    @Cacheable("pokemon")
    public List<Pokemon> getPokemonByType(String name) {
        return pokemonRepository.findByType1OrType2(name, name);
    }

    @Cacheable("pokemon")
    public List<Pokemon> getPokemonByGeneration(int id) {
        return pokemonRepository.findByGeneration(Generations.getGenerationById(id));
    }
}
