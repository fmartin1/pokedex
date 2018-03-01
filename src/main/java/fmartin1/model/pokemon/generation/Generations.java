package fmartin1.model.pokemon.generation;

public class Generations {
    public static Generation getGenerationById(int id) {
        switch (id) {
            case 1:
                return Generation.GENERATION_1;
            case 2:
                return Generation.GENERATION_2;
            case 3:
                return Generation.GENERATION_3;
            case 4:
                return Generation.GENERATION_4;
            case 5:
                return Generation.GENERATION_5;
            case 6:
                return Generation.GENERATION_6;
            case 7:
                return Generation.GENERATION_7;
            default:
                return null;
        }
    }

    public static Generation getByPokemonId(int pokemonId) {
        if (pokemonId <= Generation.GENERATION_1._lastPokemon) {
            return Generation.GENERATION_1;
        }
        if (pokemonId <= Generation.GENERATION_2._lastPokemon) {
            return Generation.GENERATION_2;
        }
        if (pokemonId <= Generation.GENERATION_3._lastPokemon) {
            return Generation.GENERATION_3;
        }
        if (pokemonId <= Generation.GENERATION_4._lastPokemon) {
            return Generation.GENERATION_4;
        }
        if (pokemonId <= Generation.GENERATION_5._lastPokemon) {
            return Generation.GENERATION_5;
        }
        if (pokemonId <= Generation.GENERATION_6._lastPokemon) {
            return Generation.GENERATION_6;
        }
        if (pokemonId <= Generation.GENERATION_7._lastPokemon) {
            return Generation.GENERATION_7;
        }
        return null;
    }
}
