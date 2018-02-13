package fmartin1.model.common;

public class Endpoint {
    private final Integer count;
    private final String previous;
    private final String next;
    private final NamedAPIResource[] results;

    public Endpoint(Integer count, String previous, String next, NamedAPIResource[] results) {
        this.count = count;
        this.previous = previous;
        this.next = next;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public String getPrevious() {
        return previous;
    }

    public String getNext() {
        return next;
    }

    public NamedAPIResource[] getResults() {
        return results;
    }
}
