package fmartin1.pokedex.service;

import fmartin1.pokedex.model.pokeapi.PokeAPIEndpoint;
import fmartin1.pokedex.model.pokeapi.PokeAPIType;
import fmartin1.pokedex.model.pokemon.Pokemon;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;

@Service("poke-data-service")
public class PokeDataService {

    private static final String URI = "https://pokeapi.co/api/v2/";

    private final RestTemplate _restTemplate = new RestTemplate();
    private final HttpEntity<String> _httpEntity;

    public PokeDataService(HttpEntity<String> httpEntity) {
        _httpEntity = httpEntity;
    }

    public LinkedHashMap<String, Pokemon> getPokemonFromPokeAPI() {
        LinkedHashMap<String, Pokemon> pokemonMap = new LinkedHashMap<>();

        String allPokemonEndpointStr = URI + "pokemon?limit=" + Pokemon.TOTAL_POKEMON;

        Arrays.stream(get(allPokemonEndpointStr, PokeAPIEndpoint.class).getResults()).forEach(resource -> {
            Pokemon p = new Pokemon();
            p.setName(resource.getName());
            p.setUrl(resource.getUrl());
            pokemonMap.put(resource.getName(), p);
        });

        Arrays.stream(get(URI + "type", PokeAPIEndpoint.class)
                .getResults())
                .map(r -> get(r.getUrl(), PokeAPIType.class)).forEach(pokeAPIType -> {
            pokeAPIType.getTypePokemonRelation().forEach(relation -> {
                String pokemonName = relation.getPokemonResource().getName();
                if (pokemonMap.containsKey(pokemonName)) {
                    Pokemon pokemon = pokemonMap.get(pokemonName);

                    if (relation.getTypeSlot() == 1) {
                        pokemon.setType1(pokeAPIType.getName());
                    } else {
                        pokemon.setType2(pokeAPIType.getName());
                    }
                }
            });
        });

        return pokemonMap;
    }

    private <T> T get(String resource, Class<T> tClass) {
        ResponseEntity<T> pokemonResponse =
                _restTemplate.exchange(resource, HttpMethod.GET, _httpEntity, tClass);
        return pokemonResponse.getBody();
    }
}
