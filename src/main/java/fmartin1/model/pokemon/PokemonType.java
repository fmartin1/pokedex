package fmartin1.model.pokemon;

import fmartin1.model.pokeapi.PokeAPINamedResource;

@SuppressWarnings("unused")
public class PokemonType {
    private final int _slot;
    private final PokeAPINamedResource _type;

    public PokemonType(int slot, PokeAPINamedResource type) {
        _slot = slot;
        _type = type;
    }

    public int getSlot() {
        return _slot;
    }

    PokeAPINamedResource getType() {
        return _type;
    }
}
