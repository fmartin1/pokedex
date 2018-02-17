package fmartin1.model.pokemon.type;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fmartin1.model.common.NamedAPIResource;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypePokemon {
    private final NamedAPIResource _pokemon;

    public NamedAPIResource getPokemon() {
        return _pokemon;
    }

    private final int _slot;

    public int getSlot() {
        return _slot;
    }

    public TypePokemon(NamedAPIResource pokemon, int slot) {
        _pokemon = pokemon;
        _slot = slot;
    }
}
