package fmartin1.pokedex.model.pokeapi;

import java.util.List;

/**
 * Created by fmartinez on 2/17/2018.
 */
public class PokeAPIType {

    private String name;

    private String url;

    private List<PokeAPITypePokemonRelation> typePokemonRelation;

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
    }

    public List<PokeAPITypePokemonRelation> getTypePokemonRelation() {
        return typePokemonRelation;
    }

    public void setPokemon(List<PokeAPITypePokemonRelation> typePokemonRelation) {
        this.typePokemonRelation = typePokemonRelation;
    }
}
