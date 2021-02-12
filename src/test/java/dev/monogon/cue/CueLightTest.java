package dev.monogon.cue;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase;

abstract public class CueLightTest extends LightPlatformCodeInsightFixture4TestCase {
    private final String relativeTestDataPath;

    public CueLightTest(String relativeTestDataPath) {
        this.relativeTestDataPath = relativeTestDataPath;
    }

    @Override
    protected String getTestDataPath() {
        return CueTests.findTestData(relativeTestDataPath);
    }
}
