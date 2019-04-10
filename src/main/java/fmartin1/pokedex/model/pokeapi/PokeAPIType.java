package fmartin1.pokedex.model.pokeapi;

import java.util.List;

/**
 * Created by fmartinez on 2/17/2018.
 */
public class PokeAPIType {

    private String _name;
    private List<PokeAPITypePokemonRelation> _typePokemonRelation;

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public List<PokeAPITypePokemonRelation> getTypePokemonRelation() {
        return _typePokemonRelation;
    }

    public void setPokemon(List<PokeAPITypePokemonRelation> typePokemonRelation) {
        _typePokemonRelation = typePokemonRelation;
    }
}
