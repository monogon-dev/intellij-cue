package dev.monogon.cue.lang.navigation;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.newStructureView.StructureViewComponent;
import com.intellij.lang.LanguageStructureViewBuilder;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.util.Disposer;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.Consumer;
import dev.monogon.cue.CueLightTest;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.intellij.testFramework.PlatformTestUtil.*;

/**
 * Copyright (C) 23.04.23 - REstore NV
 */
public class CueStructureViewElementTest extends CueLightTest {
    @Test
    public void testStructuredView() {
        doTest(false);
    }

    private void doTest(boolean overWriteResult) {
        var expectedFile = Paths.get(myFixture.getTestDataPath() + "/lang/cue/navitation/" + getTestName(true) + ".txt");
        myFixture.configureByFile("/lang/cue/navitation/" + getTestName(true) + ".cue");
        myFixture.testStructureView(svc -> {
            expandAll(svc.getTree());
            try {
                if (!overWriteResult) {
                    String expected = Files.readString(expectedFile);
                    assertTreeEqual(svc.getTree(), expected);
                } else {
                    Files.writeString(expectedFile, print(svc.getTree()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void doTestTreeStructure(@NotNull Consumer<StructureViewModel> consumer) {
        myFixture.configureByFile(getTestName(false) + ".cue");
        final StructureViewBuilder builder = LanguageStructureViewBuilder.INSTANCE.getStructureViewBuilder(myFixture.getFile());
        assertNotNull(builder);
        StructureViewComponent component = null;
        try {
            final FileEditor editor = FileEditorManager.getInstance(getProject()).getSelectedEditor(myFixture.getFile().getVirtualFile());
            component = (StructureViewComponent) builder.createStructureView(editor, myFixture.getProject());
            final StructureViewModel model = component.getTreeModel();
            consumer.consume(model);
        } finally {
            if (component != null) {
                Disposer.dispose(component);
            }
        }
    }
}