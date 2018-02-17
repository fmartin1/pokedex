package fmartin1.service;

import fmartin1.model.pokemon.Pokemon;
import fmartin1.model.pokemon.generation.Generation;
import fmartin1.model.pokemon.generation.Generations;
import fmartin1.model.pokemon.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PokedexService {

    private static final Comparator<Pokemon> TYPE_COMPARATOR = (p1, p2) -> {
        Type o1Type = p1.getType1();
        Type o2Type = p1.getType2();
        if (o1Type!=null && o2Type!=null) {
            return o1Type.getName().compareTo(o2Type.getName());
        } else if (o1Type!=null) {
            return -1;
        } else if (o2Type!=null) {
            return 1;
        } else {
            return 0;
        }
    };

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
                        type.equals(pokemon.getType1().getName()) ||
                                type.equals(pokemon.getType2().getName()))
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

    public Pokemon getPokemonById(int pokemonId) {
        int index = pokemonId - 1;
        return (0 < index && index < getPokemonMap().size()) ?
                (Pokemon) getPokemonMap().values().toArray()[index] : null;
    }

    public Pokemon getPokemonByName(String string) {
        return getPokemonMap().get(string);
    }

    public List<Pokemon> getPokemonSortedByType() {
        List<Pokemon> sortedByTypePokemonList = getAllPokemon();
        sortedByTypePokemonList.sort(TYPE_COMPARATOR);
        return sortedByTypePokemonList;
    }
}
