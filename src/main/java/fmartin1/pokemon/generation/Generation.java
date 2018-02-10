package fmartin1.pokemon.generation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fmartin1.common.NamedAPIContainer;
import fmartin1.common.NamedAPIResource;
import fmartin1.pokemon.type.PokemonSpecies;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Generation implements NamedAPIContainer {


    private Integer id;
    private String name;
    private NamedAPIResource<PokemonSpecies>[] pokemon_species;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NamedAPIResource<PokemonSpecies>[] getPokemon_species() {
        return pokemon_species;
    }

    public void setPokemon_species(NamedAPIResource<PokemonSpecies>[] pokemon_species) {
        this.pokemon_species = pokemon_species;
    }

    @Override
    public List<NamedAPIResource<PokemonSpecies>> getNamedAPIResources() {
        return Arrays.asList(pokemon_species);
    }
}
