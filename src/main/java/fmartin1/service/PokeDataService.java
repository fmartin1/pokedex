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
    public PokeDataService(PokeCSVService _pokeCSVService) {
        this._pokeCSVService = _pokeCSVService;
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
            //todo: load from PokeApi
            _pokeCSVService.writePokemonToCSV(_pokemonMap);
        }
    }
}
