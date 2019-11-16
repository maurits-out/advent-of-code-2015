package support;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class InputLoader {

    public static String loadInput(String filename) {
        var name = "./" + filename;
        var url = InputLoader.class.getClassLoader()
                .getResource(name);
        if (url == null) {
            throw new IllegalArgumentException("Resource not found: " + name);
        }
        try {
            var path = Paths.get(url.toURI());
            return Files.readString(path, ISO_8859_1);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Unable to read input: " + filename, e);
        }
    }
}
