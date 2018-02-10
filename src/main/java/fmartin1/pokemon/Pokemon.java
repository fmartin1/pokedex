package fmartin1.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fmartin1.common.NamedAPIResource;
import fmartin1.pokemon.generation.Generations;
import fmartin1.pokemon.type.Type;
import fmartin1.util.URLUtil;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pokemon implements Comparable<Pokemon> {

    private Integer id;
    private String name;
    private String url;
    private PokemonType[] types = new PokemonType[2];
    private Generations.Generation generation;

    public Pokemon(String name, String url) {
        this.name = name;
        setUrl(url);
    }

    public Pokemon() {
    }

    public Generations.Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generations.Generation generation) {
        this.generation = generation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        generation = Generations.getByPokemonId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        setId(URLUtil.getIdFromUrl(url));
    }

    public PokemonType[] getTypes() {
        return types;
    }

    public void setTypes(PokemonType[] types) {
        this.types = types;
    }

    public String getTypeName(Integer slot) {
        return types[slot-1]!=null ? types[slot-1].getType().getName():"";
    }

    public boolean hasTypeSlot(int slot) {
        return 0<slot && slot<3 && types[slot-1]!=null;
    }

    @Override
    public String toString() {
        return String.format(
                "%03d %-20s %s Types %8s %8s",
                id.intValue(), name, generation.getName(),getTypeName(1),getTypeName(2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pokemon pokemon = (Pokemon) o;

        return id.equals(pokemon.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(Pokemon o) {
        return id.compareTo(o.id);
    }
}