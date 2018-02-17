package fmartin1.service;

import fmartin1.model.pokeapi.PokeAPIEndpoint;
import fmartin1.model.pokeapi.PokeAPINamedResource;
import fmartin1.model.pokemon.Pokemon;
import fmartin1.model.pokemon.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PokeDataService {

    private final PokeCSVService _pokeCSVService;
    private final RestTemplate _restTemplate = new RestTemplate();
    private LinkedHashMap<String, Pokemon> _pokemonMap = new LinkedHashMap<>();

    @Autowired
    public PokeDataService(PokeCSVService pokeCSVService) {
        _pokeCSVService = pokeCSVService;
    }

    LinkedHashMap<String, Pokemon> getPokemonMap() {
        if (_pokemonMap.isEmpty()) {
            getPokemonFromDataSources();
        }
        return _pokemonMap;
    }

    private void getPokemonFromDataSources() {
        _pokemonMap = _pokeCSVService.loadPokemonFromCSV();
        if (_pokemonMap.isEmpty()) {
            getPokemonFromApi();
            _pokeCSVService.writePokemonToCSV(_pokemonMap);
        }
    }

    private void getPokemonFromApi() {
        //TODO: encontrar por que esto truena.
        PokeAPIEndpoint allPokemonEndpoint = _restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/", PokeAPIEndpoint.class);
        for (PokeAPINamedResource pokemonResource : allPokemonEndpoint.getResults()) {
            Pokemon pokemon = new Pokemon(pokemonResource.getName(), pokemonResource.getUrl());
            _pokemonMap.put(pokemon.getName(), pokemon);
        }

        PokeAPIEndpoint allTypesEndpoint = _restTemplate.getForObject("https://pokeapi.co/api/v2/type/", PokeAPIEndpoint.class);
        for (PokeAPINamedResource typeResource : allTypesEndpoint.getResults()) {
            Type type = _restTemplate.getForObject("https://pokeapi.co/api/v2/type/" + typeResource.getName(), Type.class);
            type.getPokemon().stream().filter(typePokemon -> _pokemonMap.containsKey(typePokemon.getPokemon().getName())).forEach(typePokemon -> {
                List<Type> types = _pokemonMap.get(typePokemon.getPokemon().getName()).getTypes();
                if (types.size() < typePokemon.getSlot()) {
                    types.add(null);
                } else {
                    types.remove(typePokemon.getSlot() - 1);
                }
                types.add(new Type(type.getName(), typePokemon.getSlot()));
            });
        }
    }
}
