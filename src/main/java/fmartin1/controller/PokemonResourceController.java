package fmartin1.controller;


import fmartin1.pokemon.Pokemon;
import fmartin1.pokemon.type.PokemonComparator;
import fmartin1.service.PokedexService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/pokemon")
public class PokemonResourceController {
    private static final PokedexService pokedexService = PokedexService.getInstance();

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getPokemonEndpoint(@RequestParam(value = "limit",required = false) Integer limit) {
        try {
            return pokedexService.getPokemonEndPoint(limit)
                    .stream()
                    .map(Pokemon::toString)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value="/hello", method = RequestMethod.GET)
    @ResponseBody
    public String helloWorld() {
        return "hello";
    }

    @RequestMapping(value = "/{string}", method = RequestMethod.GET)
    @ResponseBody
    public String getPokemon(@PathVariable("string") String string) {
        return pokedexService.getPokemon(string.toLowerCase())
                .map(Pokemon::toString)
                .orElse(String.format("Pokemon \"%s\" not found.", string));
    }

    @RequestMapping(value = "/type", method = RequestMethod.GET)
    @ResponseBody
    public String sortByType() {
        return pokedexService.getPokemonEndPoint(null)
                .stream()
                .sorted(new PokemonComparator(PokemonComparator.Criteria.TYPE))
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @RequestMapping(value = "/type/{typeName}", method = RequestMethod.GET)
    @ResponseBody
    public String getType(@PathVariable("typeName") String typeName) {
        return pokedexService.getPokemonOfType(typeName)
                .stream()
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @RequestMapping(value = "/generation", method = RequestMethod.GET)
    @ResponseBody
    public String sortByGeneration() {
        return pokedexService.getPokemonEndPoint(null)
                .stream()
                .sorted(new PokemonComparator(PokemonComparator.Criteria.GENERATION))
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @RequestMapping(value = "/generation/{generationId}", method = RequestMethod.GET)
    @ResponseBody
    public String sortByGeneration(@PathVariable("generationId") int genId) {
        return pokedexService.getPokemonOfGeneration(genId)
                .stream()
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }
}
