package dev.monogon.cue;

import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.testFramework.FileBasedTestCaseHelperEx;
import com.intellij.testFramework.Parameterized;
import junit.framework.AssertionFailedError;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parameterized test using the SDK's instead of JUnit's support.
 */
@RunWith(Parameterized.class)
public abstract class CueParameterizedLightTest extends CueLightTest {
    @Parameters(name = "{0}")
    public static List<Object[]> params() {
        return Collections.emptyList();
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<?> params(Class<?> klass) {
        try {
            var testCase = (FileBasedTestCaseHelperEx)klass.getDeclaredConstructor().newInstance();
            var testDataRoot = CueTests.findTestDataRoot();
            var relativeBasePath = testCase.getRelativeBasePath();

            var result = new ArrayList<Object[]>();
            for (var file : CueTests.findTestFiles(testDataRoot.resolve(relativeBasePath), s -> !s.contains(".after."))) {
                result.add(new Object[]{file, relativeBasePath});
            }
            return result;
        }
        catch (Exception e) {
            throw new AssertionFailedError("Parameterized test should implement FileBasedTestHelperEx");
        }
    }

    @Parameter
    public String testFileName = "";

    @Parameter(1)
    public String testDataBasePath = "";

    @Override
    protected String getTestDataPath() {
        return CueTests.findTestData(testDataBasePath);
    }

    protected String getTestFileNameAfter() {
        return FileUtilRt.getNameWithoutExtension(testFileName) + ".after." + FileUtilRt.getExtension(testFileName);
    }
}
