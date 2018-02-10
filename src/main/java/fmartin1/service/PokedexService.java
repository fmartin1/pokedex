package fmartin1.service;

import fmartin1.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PokedexService {

    private LinkedHashMap<String, Pokemon> pokemonMap;
    private static PokedexService pokedexService;

    public static PokedexService getInstance() {
        if(pokedexService==null) pokedexService = new PokedexService();
        return pokedexService;
    }

    private PokedexService() {
        super();
        pokemonMap = PokeData.getInstance().getPokemon();
    }

    public List<Pokemon> getPokemonEndPoint(Integer limit) {
        List<Pokemon> list = new ArrayList<>(pokemonMap.values());
        return list.subList(0, Optional.ofNullable(limit)
                .filter(value -> value<=pokemonMap.size())
                .orElse(pokemonMap.size()) );
    }

    public List<Pokemon> getPokemonOfType(String type) {
        return pokemonMap.values().stream()
                .filter(pokemon -> type.equals(pokemon.getTypeName(1)) ||
                                   type.equals(pokemon.getTypeName(2)))
                .collect(Collectors.toList());
    }

    public List<Pokemon> getPokemonOfGeneration(int generation) {
        return pokemonMap.values().stream()
                .filter(pokemon -> generation==pokemon.getGeneration().ordinal()+1)
                .collect(Collectors.toList());
    }

    public Optional<Pokemon> getPokemon(String string) {
        if(string.matches("\\d")) {
            int index = Integer.parseInt(string) - 1;
            return Optional.ofNullable((Pokemon)pokemonMap.values().toArray()[index]);
        } else {
            return Optional.ofNullable(pokemonMap.get(string));
        }
    }
}
