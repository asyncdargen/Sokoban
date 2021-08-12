package ru.dargen.sokoban.render;

import lombok.Data;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Resource {

    private static final String DEFAULT_RESOURCE_PATH = "/assets/";

    private static final Map<String, Resource> loadedResources = new ConcurrentHashMap<>();

    private final String name;
    private final InputStream inputStream;

    Resource(String name) {
        this.name = name;

        inputStream = getClass().getResourceAsStream(DEFAULT_RESOURCE_PATH + name);

        if (inputStream == null)
            throw new IllegalArgumentException("unknown resource " + name);
    }

    public static Resource getResource(String name) {
        return loadedResources.computeIfAbsent(name, Resource::new);
    }

}
