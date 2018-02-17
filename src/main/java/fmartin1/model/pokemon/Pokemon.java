package fmartin1.model.pokemon;

import fmartin1.model.pokeapi.PokeAPIType;
import fmartin1.model.pokemon.generation.Generation;
import fmartin1.model.pokemon.generation.Generations;
import fmartin1.model.pokemon.type.Type;

public class Pokemon implements Comparable<Pokemon> {
    private static final int ID_URL_INDEX = 6;
    private final String _name;
    private final String _url;
    private final int _id;
    private final Generation _generation;
    private Type _type1;
    private Type _type2;

    public Pokemon(String name, String url) {
        _name = name;
        _url = url;
        _id = Integer.parseInt(url.split("/")[ID_URL_INDEX]);
        _generation = Generations.getByPokemonId(_id);
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getUrl() {
        return _url;
    }

    public Type getType1() {
        return _type1;
    }

    public Type getType2() {
        return _type2;
    }

    public Generation getGeneration() {
        return _generation;
    }

    public void setType1(Type _type1) {
        this._type1 = _type1;
    }

    public void setType2(Type _type2) {
        this._type2 = _type2;
    }

    public void setTypes(PokeAPIType[] types) {
        _type1 = new Type(types[0].getName());
        if (types.length == 2) {
            _type2 = new Type(types[1].getName());
        }
    }

    @Override
    public int compareTo(Pokemon o) {
        return Integer.compare(_id, o._id);
    }
}