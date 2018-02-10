package fmartin1.pokemon.generation;

public class Generations {
    public enum Generation {
        GENERATION_1(  1,151, "Generation 01"),
        GENERATION_2(152,351, "Generation 02"),
        GENERATION_3(352,386, "Generation 03"),
        GENERATION_4(387,493, "Generation 04"),
        GENERATION_5(494,649, "Generation 05"),
        GENERATION_6(650,721, "Generation 06"),
        GENERATION_7(722,802, "Generation 07");
        int first;
        int last;
        String name;
        Generation(int first, int last, String name) {
            this.first = first;
            this.last = last;
            this.name = name;
        }

        public int getFirst() {
            return first;
        }

        public int getLast() {
            return last;
        }

        public String getName() {
            return name;
        }
    }

    public static Generation getGenerationByName(String name) {
        switch (name) {
            case "Generation 01": return Generation.GENERATION_1;
            case "Generation 02": return Generation.GENERATION_2;
            case "Generation 03": return Generation.GENERATION_3;
            case "Generation 04": return Generation.GENERATION_4;
            case "Generation 05": return Generation.GENERATION_5;
            case "Generation 06": return Generation.GENERATION_6;
            case "Generation 07": return Generation.GENERATION_7;
            default: return null;
        }
    }

    public static Generation getGenerationById(int id) {
        switch (id) {
            case 1: return Generation.GENERATION_1;
            case 2: return Generation.GENERATION_2;
            case 3: return Generation.GENERATION_3;
            case 4: return Generation.GENERATION_4;
            case 5: return Generation.GENERATION_5;
            case 6: return Generation.GENERATION_6;
            case 7: return Generation.GENERATION_7;
            default: return null;
        }
    }

    public static Generation getByPokemonId(int pokemonId) {
        if(pokemonId<=Generation.GENERATION_1.last) return Generation.GENERATION_1;
        if(pokemonId<=Generation.GENERATION_2.last) return Generation.GENERATION_2;
        if(pokemonId<=Generation.GENERATION_3.last) return Generation.GENERATION_3;
        if(pokemonId<=Generation.GENERATION_4.last) return Generation.GENERATION_4;
        if(pokemonId<=Generation.GENERATION_5.last) return Generation.GENERATION_5;
        if(pokemonId<=Generation.GENERATION_6.last) return Generation.GENERATION_6;
        if(pokemonId<=Generation.GENERATION_7.last) return Generation.GENERATION_7;
        return null;
    }
}
