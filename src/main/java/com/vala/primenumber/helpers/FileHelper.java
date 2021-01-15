package com.vala.primenumber.helpers;

import com.vala.primenumber.Main;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.lang.System.lineSeparator;
import static java.nio.file.Files.writeString;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Slf4j
public class FileHelper {

    public static void writeToFile(final String path, final String content)
            throws IOException {
        String charSequence = content + lineSeparator();
        writeString(Paths.get(path), charSequence, CREATE, APPEND);
    }

    public static String getFilePath(final String fileName) {
        String path = Main.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        File file = new File(path);
        return file.getParentFile().getAbsolutePath() + "/" + fileName;
    }
}
