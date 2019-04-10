package fmartin1.pokedex.controller;


import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value="/")
public class PokedexController {
    private final PokedexService _pokedexService;

    @Autowired
    public PokedexController(PokedexService pokedexService) {
        _pokedexService = pokedexService;
    }

    @RequestMapping(value = "/")
    public List<Pokemon> getAllPokemon(@RequestParam(value = "maxPokemonCount", required = false) Integer maxPokemonCount) {
        return _pokedexService.getAllPokemon(maxPokemonCount);
    }

    @RequestMapping(value = "/id/{pokemonId}")
    public Pokemon getPokemonById(@PathVariable("pokemonId") int pokemonId) {
        return _pokedexService.getPokemonById(pokemonId);
    }

    @RequestMapping(value = "/name/{pokemonName}")
    public Pokemon getPokemonByName(@PathVariable("pokemonName") String pokemonName) {
        return _pokedexService.getPokemonByName(pokemonName.toLowerCase());
    }

    @RequestMapping(value = "/type")
    public List<Pokemon> sortPokemonByType() {
        return _pokedexService.getPokemonSortedByType();
    }

    @RequestMapping(value = "/type/{typeName}")
    public List<Pokemon> getPokemonByType(@PathVariable("typeName") String typeName) {
        return _pokedexService.getPokemonOfType(typeName);
    }

    @RequestMapping(value = "/generation")
    public List<Pokemon> sortPokemonByGeneration() {
        return _pokedexService.getAllPokemon();
    }

    @RequestMapping(value = "/generation/{generationId}")
    public List<Pokemon> getPokemonInGeneration(@PathVariable("generationId") int genId) {
        return _pokedexService.getPokemonOfGeneration(genId);
    }
}
