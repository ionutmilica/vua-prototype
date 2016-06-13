package vua.params.Extractors;

import vua.http.Context;

public abstract class BaseArgumentExtractor<T> {

    protected Class<?> concreteClass;

    public abstract T extract(Context context);

    public abstract Class<T> getExtractedType();

    public abstract String getFieldName();

    public void setConcreteClass(Class<?> concreteClass) {
        this.concreteClass = concreteClass;
    }
}
