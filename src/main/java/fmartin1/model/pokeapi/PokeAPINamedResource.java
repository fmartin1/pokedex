package fmartin1.model.pokeapi;

public class PokeAPINamedResource {
    private String _name;
    private String _url;

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        _url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokeAPINamedResource that = (PokeAPINamedResource) o;

        return _url.equals(that._url);
    }

    @Override
    public int hashCode() {
        return _url.hashCode();
    }
}
