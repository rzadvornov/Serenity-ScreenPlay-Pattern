/*
 *
 *  * Copyright (c) Roman Zadvornov
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *
 *
 */

package com.rzadvornov.serenity.config;


import com.rzadvornov.serenity.utilities.JsonParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnvConfigs {

    private EnvConfigs() {
    }

    private static final Map<String, Object> ENV_CONFIG = new HashMap<>();

    public static void setConfigs() {
        ENV_CONFIG.putAll(Objects.requireNonNull(JsonParser.parseConfigFile()));
    }

    public static Object getConfig(final String key) {
        return ENV_CONFIG.get(key);
    }
}
