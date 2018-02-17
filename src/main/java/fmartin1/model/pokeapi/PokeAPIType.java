package fmartin1.model.pokeapi;

import java.util.List;

/**
 * Created by fmartinez on 2/17/2018.
 */
public class PokeAPIType {
    private final String _name;
    private final List<PokeAPITypePokemonRelation> _typePokemonRelation;

    public PokeAPIType(String name, List<PokeAPITypePokemonRelation> typePokemonRelation) {
        _name = name;
        _typePokemonRelation = typePokemonRelation;
    }

    public String getName() {
        return _name;
    }

    public List<PokeAPITypePokemonRelation> get_typePokemonRelation() {
        return _typePokemonRelation;
    }
}
