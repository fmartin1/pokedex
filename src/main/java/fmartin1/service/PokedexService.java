package fmartin1.service;

import fmartin1.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PokedexService {

    private final PokeDataService _pokeDataService;

    @Autowired
    public PokedexService(PokeDataService pokeDataService) {
        _pokeDataService = pokeDataService;
    }

    public List<Pokemon> getPokemonEndPoint(Integer limit) {
        List<Pokemon> list = new ArrayList<>(getPokemonMap().values());
        return list.subList(0, Optional.ofNullable(limit)
                .filter(value -> value <= getPokemonMap().size())
                .orElse(getPokemonMap().size()));
    }

    private LinkedHashMap<String, Pokemon> getPokemonMap() {
        return _pokeDataService.getPokemonMap();
    }

    public List<Pokemon> getPokemonOfType(String type) {
        return getPokemonMap().values().stream()
                .filter(pokemon -> type.equals(pokemon.getTypeName(1)) ||
                        type.equals(pokemon.getTypeName(2)))
                .collect(Collectors.toList());
    }

    public List<Pokemon> getPokemonOfGeneration(int generation) {
        return getPokemonMap().values().stream()
                .filter(pokemon -> generation == pokemon.getGeneration().ordinal() + 1)
                .collect(Collectors.toList());
    }

    public Optional<Pokemon> getPokemon(String string) {
        if (string.matches("\\d")) {
            int index = Integer.parseInt(string) - 1;
            return Optional.ofNullable((Pokemon) getPokemonMap().values().toArray()[index]);
        } else {
            return Optional.ofNullable(getPokemonMap().get(string));
        }
    }
}
