package fmartin1.pokemon.type;


import fmartin1.common.NamedAPIResource;
import fmartin1.pokemon.Pokemon;
import fmartin1.util.URLUtil;

public class TypePokemon {
    private Integer slot;
    private NamedAPIResource<Pokemon> pokemon;

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public NamedAPIResource<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(NamedAPIResource<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    public int getId() {
        return URLUtil.getIdFromUrl(pokemon.getUrl());
    }
}
