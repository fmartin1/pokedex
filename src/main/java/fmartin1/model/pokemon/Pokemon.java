package fmartin1.model.pokemon;

import fmartin1.model.pokemon.generation.Generation;
import fmartin1.model.pokemon.generation.Generations;

import java.util.Optional;

public class Pokemon implements Comparable<Pokemon> {
    public static final int TOTAL_POKEMON = 802;
    private static final int ID_URL_INDEX = 6;
    private final String _name;
    private final String _url;
    private final int _id;
    private final Generation _generation;
    private String _type1;
    private String _type2;

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

    public String getType1() {
        return _type1;
    }

    public void setType1(String type1) {
        _type1 = type1;
    }

    public String getType2() {
        return _type2;
    }

    public Optional<String> getOptionalType2() {
        return Optional.ofNullable(_type2);
    }

    public void setType2(String type2) {
        _type2 = type2;
    }

    public Generation getGeneration() {
        return _generation;
    }

    @Override
    public int compareTo(Pokemon o) {
        return Integer.compare(_id, o._id);
    }
}