package fmartin1.service;

import fmartin1.model.pokemon.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class PokeDataService {

    private final PokeCSVService _pokeCSVService;
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
            _pokeCSVService.writePokemonToCSV(_pokemonMap);
        }
    }
}
