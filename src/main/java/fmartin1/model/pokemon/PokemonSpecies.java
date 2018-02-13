package fmartin1.model.pokemon;

@SuppressWarnings("unused")
public class PokemonSpecies {
    private final String _name;
    private final String _url;

    public PokemonSpecies(String name, String url) {
        _name = name;
        _url = url;
    }

    public String get_name() {
        return _name;
    }

    public String get_url() {
        return _url;
    }
}
