package dev.monogon.cue.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.util.PsiTreeUtil;
import dev.monogon.cue.lang.CueFileType;
import dev.monogon.cue.lang.CueLanguage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CueFile extends PsiFileBase implements CueBlock  {
    public CueFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CueLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return CueFileType.INSTANCE;
    }

    public @NotNull List<CueImportDecl> getImportDeclarations() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, CueImportDecl.class);
    }

    public List<CueField> getFields() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, CueField.class);
    }
}
