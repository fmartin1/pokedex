package fmartin1.model;

import fmartin1.model.common.NamedAPIResource;
import fmartin1.model.type.Type;

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
