package fmartin1.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper _objectMapper = new ObjectMapper();

    @Autowired
    public PokedexController(PokedexService _pokedexService) {
        this._pokedexService = _pokedexService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getAllPokemon(@RequestParam(value = "limit", required = false) Integer limit) {
        String response = "";
        try {
            StringBuilder json = new StringBuilder("[");
            for (Pokemon pokemon : _pokedexService.getAllPokemon(limit)) {
                json.append(_objectMapper.writeValueAsString(pokemon)).append(",");
            }
            json.deleteCharAt(json.length() - 1);
            json.append("]");
            response = json.toString();
        } catch (JsonProcessingException ignored) {
        }
        return response;
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
