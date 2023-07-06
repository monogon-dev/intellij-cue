// This is a generated file. Not intended for manual editing.
package dev.monogon.cue.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;

public interface CueField extends CueDeclaration {

  @NotNull
  CueExpression getExpression();

  @NotNull
  List<CueLabel> getLabelList();

  @NotNull
  List<CueAttribute> getAttributeList();

  @Nullable ItemPresentation getPresentation();

}
