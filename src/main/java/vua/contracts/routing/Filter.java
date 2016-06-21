package vua.contracts.routing;

import vua.http.Context;
import vua.http.Response;

public interface Filter {

    Response handle(Context context, FilterChain chain);
}
