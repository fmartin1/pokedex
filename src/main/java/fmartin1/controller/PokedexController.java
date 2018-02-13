package fmartin1.controller;


import fmartin1.model.pokemon.Pokemon;
import fmartin1.model.pokemon.PokemonComparator;
import fmartin1.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Controller
@RequestMapping("pokemon")
public class PokedexController {
    private final PokedexService _pokedexService;

    @Autowired
    public PokedexController(PokedexService pokedexService) {
        _pokedexService = pokedexService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getAllPokemon(@RequestParam(value = "limit", required = false) Integer limit) {
        return _pokedexService.getAllPokemon(limit)
                .stream()
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @RequestMapping(value = "/{pokemonIdName}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getPokemon(@PathVariable("pokemonIdName") String pokemonIdName) {
        return _pokedexService.getPokemon(pokemonIdName.toLowerCase())
                .map(Pokemon::toString)
                .orElse(String.format("Pokemon \"%s\" not found.", pokemonIdName));
    }

    @RequestMapping(value = "/type", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String sortByType() {
        return _pokedexService.getAllPokemon()
                .stream()
                .sorted(new PokemonComparator(PokemonComparator.Criteria.TYPE))
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @RequestMapping(value = "/type/{typeName}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getType(@PathVariable("typeName") String typeName) {
        return _pokedexService.getPokemonOfType(typeName)
                .stream()
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @RequestMapping(value = "/generation", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String sortByGeneration() {
        return _pokedexService.getAllPokemon()
                .stream()
                .sorted(new PokemonComparator(PokemonComparator.Criteria.GENERATION))
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @RequestMapping(value = "/generation/{generationId}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String sortByGeneration(@PathVariable("generationId") int genId) {
        return _pokedexService.getPokemonOfGeneration(genId)
                .stream()
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }
}
