package fmartin1.pokedex.repository;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.model.pokemon.generation.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    Pokemon findByName(String name);

    List<Pokemon> findByType1OrType2(String name, String name1);

    List<Pokemon> findByGeneration(Generation generation);
}
