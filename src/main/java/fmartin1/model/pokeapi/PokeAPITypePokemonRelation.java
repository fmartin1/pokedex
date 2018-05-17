package fmartin1.model.pokeapi;

/**
 * Created by fmartinez on 2/17/2018.
 */
public class PokeAPITypePokemonRelation {
    private PokeAPINamedResource _pokemonResource;
    private int _typeSlot;

    public PokeAPINamedResource getPokemonResource() {
        return _pokemonResource;
    }

    public void setPokemon(PokeAPINamedResource pokemonResource) {
        _pokemonResource = pokemonResource;
    }

    public int getTypeSlot() {
        return _typeSlot;
    }

    public void setSlot(int typeSlot) {
        _typeSlot = typeSlot;
    }
}
