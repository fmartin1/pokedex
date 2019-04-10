package fmartin1.pokedex.model.pokemon.generation;

public enum Generation {
    GENERATION_0(0, 0, "No Generation Found"),
    GENERATION_1(1, 151, "Generation 01"),
    GENERATION_2(152, 351, "Generation 02"),
    GENERATION_3(352, 386, "Generation 03"),
    GENERATION_4(387, 493, "Generation 04"),
    GENERATION_5(494, 649, "Generation 05"),
    GENERATION_6(650, 721, "Generation 06"),
    GENERATION_7(722, 802, "Generation 07");
    int _firstPokemon;
    int _lastPokemon;
    String _name;

    Generation(int first, int last, String name) {
        _firstPokemon = first;
        _lastPokemon = last;
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public boolean isOriginFor(int pokemonId) {
        return _firstPokemon <= pokemonId && pokemonId <= _lastPokemon;
    }
}
