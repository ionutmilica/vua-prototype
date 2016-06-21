package vua.routing.filters;

import com.google.inject.Provider;
import vua.contracts.routing.FilterChain;
import vua.http.Context;
import vua.http.Response;
import vua.params.MethodInvoker;

public class FilterChainEnd implements FilterChain {
    private Response response;
    private Provider<?> controllerProvider;
    private MethodInvoker invoker;

    public FilterChainEnd(Response response) {
        this.response = response;
    }

    public FilterChainEnd(Provider<?> controllerProvider, MethodInvoker invoker) {
        this.controllerProvider = controllerProvider;
        this.invoker = invoker;
    }

    @Override
    public Response next(Context context) {
        if (response != null) {
            return response;
        }
        return (Response) invoker.invoke(controllerProvider.get(), context);
    }
}
