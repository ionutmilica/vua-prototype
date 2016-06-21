package vua.contracts.routing;

import vua.http.Context;
import vua.http.Response;

public interface FilterChain {
    Response next(Context context);
}
