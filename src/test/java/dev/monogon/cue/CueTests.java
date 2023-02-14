package dev.monogon.cue;

import com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Static helpers for CUE tests.
 */
public class CueTests {
    private CueTests() {
    }

    public static String findTestData(@NotNull String first, String... more) {
        return findTestDataPath(first, more).toString();
    }

    /**
     * @return The path to the test resource at the given path.
     */
    @NotNull
    public static Path findTestDataRoot() {
        var resource = CueTests.class.getResource("/testMarker.txt");
        if (resource == null) {
            throw new IllegalStateException("unable to locate testMarker.txt");
        }

        try {
            var base = Paths.get(resource.toURI());
            while (Files.exists(base)) {
                var root = base.resolve(Paths.get("src", "test", "data"));
                if (Files.exists(root)) {
                    return root;
                }
                base = base.getParent();
            }
            throw new IllegalStateException("unable to locate test data root src/test/data");
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return The path to the test resource at the given path.
     */
    @NotNull
    public static Path findTestDataPath(@NotNull String first, String... more) {
        return findTestDataRoot().resolve(Paths.get(first, more));
    }

    public static Iterable<String> findTestFiles(Path basePath) {
        return findTestFiles(basePath, o -> true);
    }

    public static Iterable<String> findTestFiles(Path basePath, @NotNull Predicate<String> accepted) {
        try {
            return Files.find(basePath, Integer.MAX_VALUE, (path, attributes) -> {
                    return Files.isRegularFile(path) && FileUtilRt.extensionEquals(path.getFileName().toString(), "cue");
                }, FileVisitOption.FOLLOW_LINKS)
                .map(basePath::relativize)
                .map(Path::toString)
                .sorted()
                .filter(accepted)
                .collect(Collectors.toList());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
