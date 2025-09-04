/*
 *
 *  * Copyright (c) Roman Zadvornov
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *
 *
 */

package com.rzadvornov.serenity.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rzadvornov.serenity.constant.StringConstant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalFileUtilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileUtilities.class);

    private LocalFileUtilities() {
    }

    public static String getFilePath(final String fileName) {
        return StringConstant.RESOURCES_PATH + fileName;
    }

    public static String readFile(final String path) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            LOGGER.error("Unable to read file: {}", e.getMessage());
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
