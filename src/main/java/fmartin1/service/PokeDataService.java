package fmartin1.service;

import fmartin1.model.pokeapi.PokeAPIEndpoint;
import fmartin1.model.pokeapi.PokeAPINamedResource;
import fmartin1.model.pokeapi.PokeAPIType;
import fmartin1.model.pokeapi.PokeAPITypePokemonRelation;
import fmartin1.model.pokemon.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Service("poke-data-service")
public class PokeDataService {

    private static final String URI = "https://pokeapi.co/api/v2/";

    private final PokeCSVService _pokeCSVService;
    private final RestTemplate _restTemplate = new RestTemplate();
    private final HttpHeaders _httpHeaders = new HttpHeaders();
    private final HttpEntity<String> _httpEntity;
    private LinkedHashMap<String, Pokemon> _pokemonMap = new LinkedHashMap<>();

    @Autowired
    public PokeDataService(PokeCSVService pokeCSVService) {
        _pokeCSVService = pokeCSVService;

        _httpHeaders.set("User-Agent", "poke api");

        _httpEntity = new HttpEntity<>(_httpHeaders);
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
            _pokemonMap = getPokemonFromPokeAPI();
            _pokeCSVService.writePokemonToCSV(_pokemonMap);
        }
    }

    public LinkedHashMap<String, Pokemon> getPokemonFromPokeAPI() {
        LinkedHashMap<String, Pokemon> pokemonMap = new LinkedHashMap<>();

        String allPokemonEndpointStr = URI + "pokemon?limit=" + Pokemon.TOTAL_POKEMON;

        for (PokeAPINamedResource r : get(allPokemonEndpointStr, PokeAPIEndpoint.class).getResults()) {
            pokemonMap.put(r.getName(), new Pokemon(r.getName(), r.getUrl()));
        }

        for (PokeAPINamedResource r : get(URI + "type", PokeAPIEndpoint.class).getResults()) {
            PokeAPIType pokeAPIType = get(r.getUrl(), PokeAPIType.class);

            for (PokeAPITypePokemonRelation relation : pokeAPIType.getTypePokemonRelation()) {
                String pokemonName = relation.getPokemonResource().getName();

                if (pokemonMap.containsKey(pokemonName)) {
                    Pokemon pokemon = pokemonMap.get(pokemonName);

                    if (relation.getTypeSlot() == 1) {
                        pokemon.setType1(pokeAPIType.getName());
                    } else {
                        pokemon.setType2(pokeAPIType.getName());
                    }
                }
            }
        }

        return pokemonMap;
    }

    private <T> T get(String resource, Class<T> tClass) {
        ResponseEntity<T> pokemonResponse =
                _restTemplate.exchange(resource, HttpMethod.GET, _httpEntity, tClass);
        return pokemonResponse.getBody();
    }
}
