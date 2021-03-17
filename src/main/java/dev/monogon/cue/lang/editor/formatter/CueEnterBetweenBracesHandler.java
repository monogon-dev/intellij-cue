package dev.monogon.cue.lang.editor.formatter;

import com.intellij.codeInsight.editorActions.enter.EnterBetweenBracesDelegate;

/**
 * Adds the enter handling for []. By default, only {} and () would be supported.
 */
public class CueEnterBetweenBracesHandler extends EnterBetweenBracesDelegate {
    @Override
    protected boolean isBracePair(char left, char right) {
        return (left == '(' && right == ')')
               || (left == '{' && right == '}')
               || (left == '[' && right == ']');
    }
}
