package dev.monogon.cue.lang;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import dev.monogon.cue.lang.lexer.CueCommaInsertingLexer;
import dev.monogon.cue.lang.parser.CueParser;
import dev.monogon.cue.lang.psi.CueFile;
import dev.monogon.cue.lang.psi.CueFileElementType;
import org.jetbrains.annotations.NotNull;

public class CueParserDefinition implements ParserDefinition {
    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new CueCommaInsertingLexer();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new CueParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return CueFileElementType.INSTANCE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return CueTokenTypes.COMMENTS;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return CueTokenTypes.STRING_LITERALS;
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return CueTypes.Factory.createElement(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new CueFile(viewProvider);
    }
}
