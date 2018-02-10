package fmartin1.pokemon;

import fmartin1.common.NamedAPIResource;
import fmartin1.pokemon.type.Type;

public class PokemonType {
    private int slot;
    private NamedAPIResource<Type> type;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public NamedAPIResource<Type> getType() {
        return type;
    }

    public void setType(NamedAPIResource<Type> type) {
        this.type = type;
    }

    public PokemonType(int slot, NamedAPIResource<Type> type) {
        this.slot = slot;
        this.type = type;
    }

    public PokemonType() {
    }
}
