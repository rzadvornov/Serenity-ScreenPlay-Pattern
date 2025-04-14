/*
 *
 *  * Copyright (c) Roman Zadvornov
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *
 *
 */

package org.softindustry.com.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static org.softindustry.com.utilities.LocalFileUtilities.readFile;

public class JsonParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonParser.class);

    private JsonParser() {
    }

    public static Map<String, Object> parseConfigFile() {
        String configValue = readFile(LocalFileUtilities.getFilePath("configs/main.json"));
        try {
            return new ObjectMapper().readValue(configValue, new TypeReference<>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

}
