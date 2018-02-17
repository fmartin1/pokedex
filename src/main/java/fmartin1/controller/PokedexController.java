package fmartin1.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fmartin1.model.common.NamedAPIResource;
import fmartin1.model.pokemon.Pokemon;
import fmartin1.model.pokemon.PokemonComparator;
import fmartin1.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/{pokemonIdName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getPokemon(@PathVariable("pokemonIdName") String pokemonIdName) {
        try {
            Pokemon pokemon = _pokedexService.getPokemon(pokemonIdName.toLowerCase());
            return pokemon != null ? _objectMapper.writeValueAsString(pokemon) : _objectMapper.writeValueAsString(new NamedAPIResource("Resource not found."));
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }

    @RequestMapping(value = "/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String sortByType() {
        String response = "";
        try {
            StringBuilder json = new StringBuilder("[");
            List<Pokemon> pokemonSortedByType = _pokedexService.getAllPokemon();
            pokemonSortedByType.sort(new PokemonComparator(PokemonComparator.Criteria.TYPE));
            for (Pokemon pokemon : pokemonSortedByType) {
                json.append(_objectMapper.writeValueAsString(pokemon)).append(",");
            }
            json.deleteCharAt(json.length() - 1);
            json.append("]");
            response = json.toString();
        } catch (JsonProcessingException ignored) {
        }
        return response;
    }

    @RequestMapping(value = "/type/{typeName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getType(@PathVariable("typeName") String typeName) {
        String response = "";
        try {
            StringBuilder json = new StringBuilder("[");
            for (Pokemon pokemon : _pokedexService.getPokemonOfType(typeName)) {
                json.append(_objectMapper.writeValueAsString(pokemon)).append(",");
            }
            json.deleteCharAt(json.length() - 1);
            json.append("]");
            response = json.toString();
        } catch (JsonProcessingException ignored) {
        }
        return response;
    }

    @RequestMapping(value = "/generation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String sortByGeneration() {
        return getAllPokemon(null);
    }

    @RequestMapping(value = "/generation/{generationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String sortByGeneration(@PathVariable("generationId") int genId) {
        try {
            return _objectMapper.writeValueAsString(_pokedexService.getPokemonOfGeneration(genId));
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }

    @RequestMapping(value = "/list")
    public String getList(Model model) {
        System.out.println("algo");
        model.addAttribute("name", "Jorge");
        return "/WEB-INF/views/PokemonListCard.hbs";
    }
}
