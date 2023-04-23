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

import static com.intellij.testFramework.PlatformTestUtil.assertTreeEqual;
import static com.intellij.testFramework.PlatformTestUtil.expandAll;

/**
 * Copyright (C) 23.04.23 - REstore NV
 */
//public class CueStructureViewElementTest extends CueLightTest {
//    public void testShorthand() {
//        doTest("-Shorthand.cue" +
//                "   job.myTask.replicas");
//    }
//
//    private void doTest(final String expected) {
//        myFixture.configureByFile(getTestName(false) + ".cue");
//        myFixture.testStructureView(svc -> {
//            expandAll(svc.getTree());
//            assertTreeEqual(svc.getTree(), expected);
//        });
//    }
//
//    private void doTestTreeStructure(@NotNull Consumer<StructureViewModel> consumer) {
//        myFixture.configureByFile(getTestName(false) + ".cue");
//        final StructureViewBuilder builder = LanguageStructureViewBuilder.INSTANCE.getStructureViewBuilder(myFixture.getFile());
//        assertNotNull(builder);
//        StructureViewComponent component = null;
//        try {
//            final FileEditor editor = FileEditorManager.getInstance(getProject()).getSelectedEditor(myFixture.getFile().getVirtualFile());
//            component = (StructureViewComponent) builder.createStructureView(editor, myFixture.getProject());
//            final StructureViewModel model = component.getTreeModel();
//            consumer.consume(model);
//        } finally {
//            if (component != null) {
//                Disposer.dispose(component);
//            }
//        }
//    }
//}