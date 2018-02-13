package fmartin1.service;

import fmartin1.model.pokemon.Pokemon;
import fmartin1.model.pokemon.generation.Generation;
import fmartin1.model.pokemon.generation.Generations;
import fmartin1.model.pokemon.type.Type;
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

    public List<Pokemon> getAllPokemon(Integer limit) {
        List<Pokemon> list = new ArrayList<>(getPokemonMap().values());
        return list.subList(0, Optional.ofNullable(limit)
                .filter(value -> value <= getPokemonMap().size())
                .orElse(getPokemonMap().size()));
    }

    public List<Pokemon> getAllPokemon() {
        return getAllPokemon(null);
    }

    private LinkedHashMap<String, Pokemon> getPokemonMap() {
        return _pokeDataService.getPokemonMap();
    }

    public List<Pokemon> getPokemonOfType(String type) {
        return getPokemonMap().values().stream()
                .filter(pokemon ->
                        type.equals(pokemon.getType(Type.Slot.FIRST).map(Type::getName).orElse(null)) ||
                        type.equals(pokemon.getType(Type.Slot.SECOND).map(Type::getName).orElse(null)))
                .collect(Collectors.toList());
    }

    public List<Pokemon> getPokemonOfGeneration(int generationId) {
        return getPokemonMap().values().stream()
                .filter(pokemon -> {
                    Generation generation = Generations.getGenerationById(generationId);
                    return generation != null && generation.isOriginFor(pokemon.getId() + 1);
                })
                .collect(Collectors.toList());
    }

    public Optional<Pokemon> getPokemon(String string) {
        if (string.matches("\\d+")) {
            int index = Integer.parseInt(string) - 1;
            return Optional.ofNullable((0 < index && index < getPokemonMap().size()) ?
                    (Pokemon) getPokemonMap().values().toArray()[index] : null);
        } else {
            return Optional.ofNullable(getPokemonMap().get(string));
        }
    }
}
