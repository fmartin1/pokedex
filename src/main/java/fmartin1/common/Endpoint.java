package fmartin1.common;

public class Endpoint {
    private Integer count;
    private String previous;
    private String next;
    private NamedAPIResource[] results;

    public Endpoint() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public NamedAPIResource[] getResults() {
        return results;
    }

    public void setResults(NamedAPIResource[] results) {
        this.results = results;
    }
}
