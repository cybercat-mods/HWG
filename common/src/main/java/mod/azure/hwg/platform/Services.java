package mod.azure.hwg.platform;


import mod.azure.hwg.platform.services.CommonRegistry;

import java.util.ServiceLoader;

public class Services {
    public static final CommonRegistry COMMON_REGISTRY = load(CommonRegistry.class);

    private Services() {
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
