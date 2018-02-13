package fmartin1.model.pokemon;

import fmartin1.model.common.NamedAPIResource;

@SuppressWarnings("unused")
public class PokemonType {
    private final int _slot;
    private final NamedAPIResource _type;

    public PokemonType(int slot, NamedAPIResource type) {
        _slot = slot;
        _type = type;
    }

    public int getSlot() {
        return _slot;
    }

    NamedAPIResource getType() {
        return _type;
    }
}
