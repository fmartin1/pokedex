package fmartin1.model.pokeapi;

/**
 * Created by fmartinez on 2/17/2018.
 */
public class PokeAPITypePokemonRelation {
    private final PokeAPINamedResource _pokemonResource;
    private final int _typeSlot;

    public PokeAPITypePokemonRelation(PokeAPINamedResource pokemon, int slot) {
        _pokemonResource = pokemon;
        _typeSlot = slot;
    }

    public PokeAPINamedResource getPokemonResource() {
        return _pokemonResource;
    }

    public int getTypeSlot() {
        return _typeSlot;
    }
}
