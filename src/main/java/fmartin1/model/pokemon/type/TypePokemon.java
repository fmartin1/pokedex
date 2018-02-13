package fmartin1.model.pokemon.type;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fmartin1.model.common.NamedAPIResource;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypePokemon {
    private final NamedAPIResource _pokemon;

    public TypePokemon(NamedAPIResource pokemon) {
        _pokemon = pokemon;
    }

    public NamedAPIResource getPokemon() {
        return _pokemon;
    }
}
