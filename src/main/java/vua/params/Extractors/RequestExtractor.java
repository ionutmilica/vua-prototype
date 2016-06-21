package vua.params.Extractors;

import vua.http.Context;
import vua.http.Request;

public class RequestExtractor extends BaseArgumentExtractor<Request> {

    @Override
    public Request extract(Context context) {
        return context.getRequest();
    }

    @Override
    public Class<Request> getExtractedType() {
        return Request.class;
    }

    @Override
    public String getFieldName() {
        return null;
    }
}
