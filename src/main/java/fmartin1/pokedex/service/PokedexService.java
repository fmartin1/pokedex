package fmartin1.pokedex.service;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.model.pokemon.generation.Generation;
import fmartin1.pokedex.model.pokemon.generation.Generations;
import fmartin1.pokedex.repository.PokemonRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "${pokedex.cache.name}")
public class PokedexService {

    private PokemonRepository pokemonRepository;

    public PokedexService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Cacheable(key = "#pokemon.name")
    public Pokemon postPokemon(Pokemon pokemon) {
        if (pokemon.getGeneration() == Generation.GENERATION_0) {
            throw new IllegalArgumentException("Generation 0 is not valid.");
        }
        return pokemonRepository.save(pokemon);
    }

    @Cacheable
    public Pokemon findPokemon(String name) {
        return pokemonRepository.findByName(name.toLowerCase());
    }

    @Cacheable(key = "#result.name")
    public Pokemon findPokemon(int id) {
        return pokemonRepository.findById(id);
    }

    @Cacheable(key = "#result.name")
    public List<Pokemon> getPokemonByType(String name) {
        return pokemonRepository.findByType1OrType2(name, name);
    }

    @Cacheable(key = "#result.name")
    public List<Pokemon> getPokemonByGeneration(int id) {
        return pokemonRepository.findByGeneration(Generations.getGenerationById(id));
    }
}
