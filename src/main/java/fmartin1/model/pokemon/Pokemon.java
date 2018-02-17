package fmartin1.model.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fmartin1.model.pokeapi.PokeAPINamedResource;
import fmartin1.model.pokemon.generation.Generation;
import fmartin1.model.pokemon.generation.Generations;
import fmartin1.model.pokemon.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon extends PokeAPINamedResource implements Comparable<Pokemon> {
    private static final int ID_URL_INDEX = 6;
    private final int _id;
    private final Generation _generation;

    public List<Type> getTypes() {
        return _types;
    }

    private final List<Type> _types = new ArrayList<>();

    public Pokemon(String name, String url) {
        super(name, url);
        _id = Integer.parseInt(url.split("/")[ID_URL_INDEX]);
        _generation = Generations.getByPokemonId(_id);
    }

    public int getId() {
        return _id;
    }

    public Optional<Type> getType(Type.Slot slot) {
        return Optional.ofNullable(_types.size() <= slot.getIndex() ? null : _types.get(slot.getIndex()));
    }

    public void setTypes(List<Type> types) {
        _types.clear();
        _types.addAll(types);
    }

    @Override
    public String toString() {
        return String.format("%03d %-20s %s Types %8s %8s", _id, _name, _generation.getName(),
                getType(Type.Slot.FIRST).map(Type::getName).orElse(""),
                getType(Type.Slot.SECOND).map(Type::getName).orElse(""));
    }

    @Override
    public int compareTo(Pokemon o) {
        return Integer.compare(_id, o._id);
    }
}