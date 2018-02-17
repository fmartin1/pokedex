package fmartin1.model.pokemon.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fmartin1.model.pokeapi.PokeAPINamedResource;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Type extends PokeAPINamedResource implements Comparable<Type> {
    private final int _slot;
    private final List<TypePokemon> _pokemon;

    public Type(String name, String url, int slot, List<TypePokemon> pokemon) {
        super(name, url);
        _slot = slot;
        _pokemon = pokemon;
    }

    public Type(String name, int slot) {
        super(name);
        _slot = slot;
        _pokemon = null;
    }

    public Type(int slot, PokeAPINamedResource type) {
        super(type.getName(),type.getUrl());
        _slot = slot;
        _pokemon = null;
    }

    public List<TypePokemon> getPokemon() {
        return _pokemon;
    }

    @Override
    public int compareTo(Type o) {
        return _name.compareTo(o._name);
    }

    public enum Slot {
        FIRST(0), SECOND(1);
        int _index;

        Slot(int index) {
            _index = index;
        }

        public int getIndex() {
            return _index;
        }
    }
}
