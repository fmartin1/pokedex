package fmartin1.pokedex.service;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.model.pokemon.generation.Generation;
import fmartin1.pokedex.model.pokemon.generation.Generations;
import fmartin1.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PokedexService {

    @Autowired
    private PokemonRepository pokemonRepository;

    private static final Comparator<Pokemon> TYPE_COMPARATOR = (p1, p2) -> {
        String o1Type = p1.getType1();
        String o2Type = p1.getOptionalType2().orElse("");

        return o1Type.compareTo(o2Type);
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
                        type.equals(pokemon.getType1()) ||
                                type.equals(pokemon.getType2()))
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

    /**
     * Load pokemon from PokeApi and store them in the DB.
     */
    public void dbRefresh() {
        LinkedHashMap<String, Pokemon> pokemonMap = _pokeDataService.getPokemonFromPokeAPI();
        pokemonRepository.save(pokemonMap.values());
    }
}
