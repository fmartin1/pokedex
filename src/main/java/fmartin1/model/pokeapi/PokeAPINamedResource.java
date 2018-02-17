package fmartin1.model.pokeapi;

public class PokeAPINamedResource {
    protected final String _name;
    private final String _url;

    public PokeAPINamedResource(String name, String url) {
        _name = name;
        _url = url;
    }

    public PokeAPINamedResource(String name) {
        _name = name;
        _url = null;
    }

    public String getName() {
        return _name;
    }

    public String getUrl() {
        return _url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokeAPINamedResource that = (PokeAPINamedResource) o;

        assert _url != null;
        return _url.equals(that._url);
    }

    @Override
    public int hashCode() {
        assert _url != null;
        return _url.hashCode();
    }
}
