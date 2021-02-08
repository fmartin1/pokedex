package fmartin1.pokedex.service;

import fmartin1.pokedex.configuration.PokeAPIConfiguration;
import fmartin1.pokedex.model.pokeapi.PokeAPIEndpoint;
import fmartin1.pokedex.model.pokeapi.PokeAPIPokemon;
import fmartin1.pokedex.model.pokeapi.PokeAPIType;
import fmartin1.pokedex.model.pokeapi.PokeAPITypePokemonRelation;
import fmartin1.pokedex.model.pokemon.Pokemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "${pokedex.cache.name}")
public class PokeAPIClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(PokeAPIClient.class);

    private PokeAPIConfiguration pokeApiConfig;

    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpEntity<String> httpEntity;

    public PokeAPIClient(PokeAPIConfiguration pokeApiConfig) {
        this.pokeApiConfig = pokeApiConfig;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.USER_AGENT, pokeApiConfig.getUserAgent());

        httpEntity = new HttpEntity<>(httpHeaders);
    }

    @Cacheable(key = "#result.name")
    public LinkedHashMap<String, Pokemon> getPokemon() {
        LinkedHashMap<String, Pokemon> pokemonMap = new LinkedHashMap<>();

        String allPokemonEndpointStr = pokeApiConfig.getUrl() + "pokemon?limit=" + Pokemon.TOTAL_POKEMON;

        try {
            Arrays.stream(get(allPokemonEndpointStr, PokeAPIEndpoint.class).getResults()).forEach(resource -> {
                Pokemon pokemon = new Pokemon();
                pokemon.setName(resource.getName());
                pokemon.setUrl(resource.getUrl());
                pokemonMap.put(resource.getName(), pokemon);
            });
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOGGER.error("PokeAPI said: " + allPokemonEndpointStr + " Not Found.");
            }
            e.printStackTrace();
        }

        String typeEndpoint = pokeApiConfig.getUrl() + pokeApiConfig.getEndpoints().get(PokeAPIConfiguration.ENDPOINT_TYPE);
        try {
            Arrays.stream(get(typeEndpoint, PokeAPIEndpoint.class)
                    .getResults())
                    .map(result -> get(result.getUrl(), PokeAPIType.class)).forEach(pokeAPIType -> {
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
            });
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOGGER.error("PokeAPI said: " + typeEndpoint + " Not Found.");
            }
            e.printStackTrace();
        }

        return pokemonMap;
    }

    @Cacheable
    public Optional<Pokemon> getPokemon(String pokemonName) {
        Optional<Pokemon> pokemon = Optional.empty();

        String url = String.format("%s/%s/%s/", pokeApiConfig.getUrl(),
                pokeApiConfig.getEndpoints().get(PokeAPIConfiguration.ENDPOINT_POKEMON), pokemonName);
        try {
            PokeAPIPokemon apiPokemon = get(url, PokeAPIPokemon.class);
            apiPokemon.setUrl(String.format("%s/%s/%d/", pokeApiConfig.getUrl(),
                    pokeApiConfig.getEndpoints().get(PokeAPIConfiguration.ENDPOINT_POKEMON), apiPokemon.getId()));
            pokemon = Optional.of(new Pokemon(apiPokemon));
        } catch (HttpClientErrorException e) {
            LOGGER.error("Get {} message: {}", pokemonName, e.getMessage());
        }

        return pokemon;
    }

    private <T> T get(String resource, Class<T> tClass) {
        ResponseEntity<T> pokemonResponse =
                restTemplate.exchange(resource, HttpMethod.GET, httpEntity, tClass);
        return pokemonResponse.getBody();
    }
}
