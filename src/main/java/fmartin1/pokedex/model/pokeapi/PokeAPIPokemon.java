package fmartin1.pokedex.model.pokeapi;

public class PokeAPIPokemon {

    private int id;

    private String name;

    private PokeAPITypes[] types;

    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PokeAPITypes[] getTypes() {
        return types;
    }

    public void setTypes(PokeAPITypes[] types) {
        this.types = types;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
