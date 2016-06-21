package vua.routing.filters;

import com.google.inject.Provider;
import vua.contracts.routing.Filter;
import vua.contracts.routing.FilterChain;
import vua.http.Context;
import vua.http.Response;

public class FilterChainImpl implements FilterChain {
    private final Provider<? extends Filter> filterProvider;
    private final FilterChain next;

    public FilterChainImpl(Provider<? extends Filter> filterProvider, FilterChain next) {
        this.filterProvider = filterProvider;
        this.next = next;
    }

    @Override
    public Response next(Context context) {
        return filterProvider.get().handle(context, next);
    }
}
