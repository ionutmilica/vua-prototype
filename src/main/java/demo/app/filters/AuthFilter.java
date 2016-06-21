package demo.app.filters;

import vua.contracts.routing.Filter;
import vua.contracts.routing.FilterChain;
import vua.http.Context;
import vua.http.Redirect;
import vua.http.Response;

public class AuthFilter implements Filter {

    @Override
    public Response handle(Context context, FilterChain chain) {
        return Redirect.to("http://google.ro");
    }
}
