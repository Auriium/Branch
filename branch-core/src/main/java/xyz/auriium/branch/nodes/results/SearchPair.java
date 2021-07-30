package xyz.auriium.branch.nodes.results;

import xyz.auriium.branch.nodes.results.PostProcessSearch;

public class SearchPair<T,O> {

    private final PostProcessSearch<T> search;
    private final O object;

    public SearchPair(PostProcessSearch<T> search, O object) {
        this.search = search;
        this.object = object;
    }

    public PostProcessSearch<T> getSearch() {
        return search;
    }

    public O getObject() {
        return object;
    }
}
