package fmartin1;

import fmartin1.model.pokemon.Pokemon;
import fmartin1.service.PokeCSVService;
import fmartin1.service.PokeDataService;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokeApiTest {

    @Test
    public void testGetAllPokemon() {
        Map<String, Pokemon> pokemon = new PokeDataService(new PokeCSVService()).getPokemonFromPokeAPI();

        assertTrue(pokemon.size() > 0);
        assertTrue(pokemon.size() == Pokemon.TOTAL_POKEMON);

        assertEquals("grass", pokemon.get("bulbasaur").getType1());
        assertEquals("flying", pokemon.get("charizard").getType2());
        assertEquals("Generation 02",pokemon.get("chikorita").getGeneration().getName());
    }
}
