package fmartin1.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fmartin1.model.pokeapi.PokeAPINamedResource;
import fmartin1.model.pokemon.Pokemon;
import fmartin1.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String getAllPokemon(@RequestParam(value = "maxPokemonCount", required = false) Integer maxPokemonCount) {
        String response = "";
        try {
            StringBuilder json = new StringBuilder("[");
            for (Pokemon pokemon : _pokedexService.getAllPokemon(maxPokemonCount)) {
                json.append(_objectMapper.writeValueAsString(pokemon)).append(",");
            }
            json.deleteCharAt(json.length() - 1);
            json.append("]");
            response = json.toString();
        } catch (JsonProcessingException ignored) {
        }
        return response;
    }

    @RequestMapping(value = "/id/{pokemonId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getPokemonById(@PathVariable("pokemonId") int pokemonId) {
        try {
            Pokemon pokemon = _pokedexService.getPokemonById(pokemonId);
            return pokemon != null ? _objectMapper.writeValueAsString(pokemon) : _objectMapper.writeValueAsString(new PokeAPINamedResource("Resource not found."));
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }

    @RequestMapping(value = "/name/{pokemonName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getPokemonByName(@PathVariable("pokemonName") String pokemonName) {
        try {
            Pokemon pokemon = _pokedexService.getPokemonByName(pokemonName.toLowerCase());
            return pokemon != null ? _objectMapper.writeValueAsString(pokemon) : _objectMapper.writeValueAsString(new PokeAPINamedResource("Resource not found."));
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }

    @RequestMapping(value = "/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String sortPokemonByType() {
        String response = "";
        try {
            StringBuilder json = new StringBuilder("[");
            for (Pokemon pokemon : _pokedexService.getPokemonSortedByType()) {
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
    public String getPokemonByType(@PathVariable("typeName") String typeName) {
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
    public String sortPokemonByGeneration() {
        String response = "";
        try {
            StringBuilder json = new StringBuilder("[");
            for (Pokemon pokemon : _pokedexService.getAllPokemon()) {
                json.append(_objectMapper.writeValueAsString(pokemon)).append(",");
            }
            json.deleteCharAt(json.length() - 1);
            json.append("]");
            response = json.toString();
        } catch (JsonProcessingException ignored) {
        }
        return response;
    }

    @RequestMapping(value = "/generation/{generationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getPokemonInGeneration(@PathVariable("generationId") int genId) {
        try {
            return _objectMapper.writeValueAsString(_pokedexService.getPokemonOfGeneration(genId));
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }
}
