package fmartin1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fmartin1.model.generation.Generations;
import fmartin1.util.URLUtil;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pokemon implements Comparable<Pokemon> {

    private Integer _id;
    private String _name;
    private String _url;
    private PokemonType[] _types = new PokemonType[2];
    private Generations.Generation _generation;

    public Pokemon(String name, String url) {
        _name = name;
        setUrl(url);
    }

    public Pokemon() {
    }

    public Generations.Generation getGeneration() {
        return _generation;
    }

    public void setGeneration(Generations.Generation generation) {
        this._generation = generation;
    }

    public Integer getId() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
        _generation = Generations.getByPokemonId(_id);
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        this._url = url;
        set_id(URLUtil.getIdFromUrl(url));
    }

    public PokemonType[] getTypes() {
        return _types;
    }

    public void setTypes(PokemonType[] types) {
        this._types = types;
    }

    public String getTypeName(Integer slot) {
        return _types[slot-1]!=null ? _types[slot-1].getType().getName():"";
    }

    public boolean hasTypeSlot(int slot) {
        return 0<slot && slot<3 && _types[slot-1]!=null;
    }

    @Override
    public String toString() {
        return String.format(
                "%03d %-20s %s Types %8s %8s",
                _id.intValue(), _name, _generation.getName(),getTypeName(1),getTypeName(2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pokemon pokemon = (Pokemon) o;

        return _id.equals(pokemon._id);
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    @Override
    public int compareTo(Pokemon o) {
        return _id.compareTo(o._id);
    }
}