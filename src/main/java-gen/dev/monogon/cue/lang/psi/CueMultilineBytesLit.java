// This is a generated file. Not intended for manual editing.
package dev.monogon.cue.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;

public interface CueMultilineBytesLit extends CueLiteral, CueMultilineLiteral, PsiLanguageInjectionHost {

  @NotNull
  List<CueInterpolation> getInterpolationList();

  @NotNull
  PsiElement getOpeningQuote();

  @Nullable
  PsiElement getClosingQuote();

}
