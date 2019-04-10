package fmartin1.pokedex.model.pokemon;

import fmartin1.pokedex.model.pokemon.generation.Generation;
import fmartin1.pokedex.model.pokemon.generation.Generations;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name="pokemon")
public class Pokemon implements Comparable<Pokemon> {
    public static final int TOTAL_POKEMON = 802;
    private static final int ID_URL_INDEX = 6;

    @Id
    private int id;
    private String name;
    private String url;

    @Enumerated(EnumType.ORDINAL)
    private Generation generation;
    private String type1;
    private String type2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        if (url != null) {
            id = Integer.parseInt(url.split("/")[ID_URL_INDEX]);
            generation = Generations.getByPokemonId(id);
        }
    }

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public Optional<String> getOptionalType2() {
        return Optional.ofNullable(type2);
    }

    @Override
    public int compareTo(Pokemon o) {
        return Integer.compare(id, o.id);
    }
}