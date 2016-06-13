package vua.params.Extractors;

import vua.http.Context;
import vua.http.Request;
import vua.validation.FormRequest;

public class FormRequestExtractor extends BaseArgumentExtractor<FormRequest> {

    @Override
    public FormRequest extract(Context context) {
        try {
            FormRequest request = (FormRequest) concreteClass.newInstance();
            request.setRequest(context.getRequest());
            return request;
        } catch (Exception e){
            Request request = new Request();
            request.setRequest(context.getRequest());
            return (FormRequest) request;
        }
    }

    @Override
    public Class<FormRequest> getExtractedType() {
        return FormRequest.class;
    }

    @Override
    public String getFieldName() {
        return null;
    }
}
