package fmartin1;


import fmartin1.pokemon.Pokemon;
import fmartin1.pokemon.type.PokemonComparator;
import fmartin1.service.PokedexService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Path("pokemon")
public class PokemonResource {
    private static final PokedexService pokedexService = PokedexService.getInstance();

    @GET
    @Path("")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPokemonEndpoint(@QueryParam("limit") Integer limit) {
        try {
            return pokedexService.getPokemonEndPoint(limit)
                    .stream()
                    .map(Pokemon::toString)
                    .collect(Collectors.joining("\n"));
        }catch (Exception e) {e.printStackTrace();}
        return "";
    }

    @GET
    @Path("{string}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPokemon(@PathParam("string")String string) {
        return pokedexService.getPokemon(string.toLowerCase())
                .map(Pokemon::toString)
                .orElse(String.format("Pokemon \"%s\" not found.",string));
    }

    @GET
    @Path("type")
    @Produces(MediaType.TEXT_PLAIN)
    public String sortByType() {
        return pokedexService.getPokemonEndPoint(null)
                .stream()
                .sorted(new PokemonComparator(PokemonComparator.Criteria.TYPE))
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("type/{typeName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getType(@PathParam("typeName")String typeName) {
        return pokedexService.getPokemonOfType(typeName)
                .stream()
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("generation")
    @Produces(MediaType.TEXT_PLAIN)
    public String sortByGeneration() {
        return pokedexService.getPokemonEndPoint(null)
                .stream()
                .sorted(new PokemonComparator(PokemonComparator.Criteria.GENERATION))
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("generation/{generationId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sortByGeneration(@PathParam("generationId")int genId) {
        return pokedexService.getPokemonOfGeneration(genId)
                .stream()
                .map(Pokemon::toString)
                .collect(Collectors.joining("\n"));
    }
}
