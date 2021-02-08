package fmartin1.pokedex.model.pokemon;

import fmartin1.pokedex.model.pokeapi.PokeAPIPokemon;
import fmartin1.pokedex.model.pokemon.generation.Generation;
import fmartin1.pokedex.model.pokemon.generation.Generations;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "pokemon")
public class Pokemon implements Comparable<Pokemon> {
    public static final int TOTAL_POKEMON = 802;
    private static final int ID_URL_INDEX = 6;
    private static final String REGEX_CHARACTERS_ONLY = "[a-z|A-Z]+";

    private static final String POKEAPI_URL_PATTERN = "https:\\/\\/pokeapi\\.co\\/api\\/v2\\/pokemon\\/\\d+";

    @Id
    @NotNull
    @Min(1)
    @Max(TOTAL_POKEMON)
    private int id;

    @NotNull
    @Pattern(regexp = REGEX_CHARACTERS_ONLY)
    private String name;

    @NotNull
    private String url;

    @Enumerated(EnumType.STRING)
    private Generation generation;

    @NotNull
    @Pattern(regexp = REGEX_CHARACTERS_ONLY)
    private String type1;

    @Pattern(regexp = REGEX_CHARACTERS_ONLY)
    private String type2;

    public Pokemon() {
    }

    public Pokemon(PokeAPIPokemon apiPokemon) {
        id = apiPokemon.getId();
        name = apiPokemon.getName();
        url = apiPokemon.getUrl();
        type1 = apiPokemon.getTypes()[0].getSlot() == 1 ? apiPokemon.getTypes()[0].getType().getName() : apiPokemon.getTypes()[1].getType().getName();
        if(apiPokemon.getTypes().length == 2) {
            type2 = apiPokemon.getTypes()[0].getSlot() == 2 ? apiPokemon.getTypes()[0].getType().getName() : apiPokemon.getTypes()[1].getType().getName();
        }
        generation = Generations.getGenerationById(id);
    }

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
        if (url != null && url.matches(POKEAPI_URL_PATTERN)) {
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