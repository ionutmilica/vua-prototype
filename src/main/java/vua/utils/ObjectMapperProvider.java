package vua.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.inject.Provider;

public class ObjectMapperProvider implements Provider<ObjectMapper> {
    @Override
    public ObjectMapper get() {

        ObjectMapper objectMapper = new ObjectMapper();

        // Afterburner optimizes performance of Pojo to Json mapper
        objectMapper.registerModule(new AfterburnerModule());

        return objectMapper;

    }
}
