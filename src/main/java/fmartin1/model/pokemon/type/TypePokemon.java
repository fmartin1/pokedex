package fmartin1.model.pokemon.type;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fmartin1.model.pokeapi.PokeAPINamedResource;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypePokemon {
    private final PokeAPINamedResource _pokemon;

    public PokeAPINamedResource getPokemon() {
        return _pokemon;
    }

    private final int _slot;

    public int getSlot() {
        return _slot;
    }

    public TypePokemon(PokeAPINamedResource pokemon, int slot) {
        _pokemon = pokemon;
        _slot = slot;
    }
}
