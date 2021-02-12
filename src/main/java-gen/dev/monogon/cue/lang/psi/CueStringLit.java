// This is a generated file. Not intended for manual editing.
package dev.monogon.cue.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CueStringLit extends CueCompositeElement {

  @Nullable
  CueMultilineBytesLit getMultilineBytesLit();

  @Nullable
  CueMultilineStringLit getMultilineStringLit();

  @Nullable
  CueSimpleBytesLit getSimpleBytesLit();

  @Nullable
  CueSimpleStringLit getSimpleStringLit();

  @Nullable
  CueStringLit getStringLit();

}
