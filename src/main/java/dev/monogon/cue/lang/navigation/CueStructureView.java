package dev.monogon.cue.lang.navigation;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import dev.monogon.cue.lang.psi.CueFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/**
 * Copyright (C) 22.04.23 - REstore NV
 */

public class CueStructureView  extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {
    private static Logger logger = Logger.getInstance(CueStructureView.class);
    public CueStructureView(@NotNull PsiFile psiFile, @Nullable Editor editor) {
        super(psiFile, editor, new CueStructureViewElement((CueFile) psiFile));
        logger.info("handeling:" + psiFile);
        withSuitableClasses(CueFile.class);
        withSorters(Sorter.ALPHA_SORTER);
    }

    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return false;
    }

}

