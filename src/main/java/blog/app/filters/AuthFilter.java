package blog.app.filters;

import vua.contracts.routing.Filter;
import vua.contracts.routing.FilterChain;
import vua.http.Context;
import vua.http.Redirect;
import vua.http.Response;
import vua.http.Session;

public class AuthFilter implements Filter {
    public Response handle(Context context, FilterChain chain) {
        Session session = context.getRequest().session();
        if (session == null || session.get("username") == null) {
            return Redirect.to("/users/create");
        }
        return chain.next(context);
    }
}
