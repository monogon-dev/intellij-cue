package dev.monogon.cue.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.IntStack;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static dev.monogon.cue.lang.CueTypes.*;
import static dev.monogon.cue.lang.CueTokenTypes.*;

%%

%{
  public _CueLexer() {
    this((java.io.Reader)null);
  }

  private final IntStack stateStack = new IntStack(100);

  private void pushState(int state) {
      int currentState = yystate();
      if (currentState == YYINITIAL && !stateStack.empty()) {
          throw new IllegalStateException("Can't push initial state into the not empty stack");
      }
      stateStack.push(currentState);
      yybegin(state);
  }

  private void popState() {
    assert !stateStack.empty() : "States stack is empty";
    yybegin(stateStack.pop());
  }

  /** Called when an instance is reset, e.g. on incremental lexer restart */
  protected void onReset() {
    stateStack.clear();
  }
%}

%class _CueLexer
%implements FlexLexer
%unicode
%public
//%debug

%function advance
%type IElementType

// White space, formed from spaces (U+0020), horizontal tabs (U+0009), carriage returns (U+000D), and newlines (U+000A)
// IntelliJ: tokenizing newlines, which are whitespace, as separate tokens to simplify our CueCommaInsertingLexer
WHITE_SPACE=[ \t\r]
WHITE_SPACE_NEWLINE=[\n]
WHITE_SPACE_ANY=[ \t\r\n]

// https://cuelang.org/docs/references/spec/#characters
newline        = \n /* the Unicode code point U+000A */
unicode_char   = [^\n] /* an arbitrary Unicode code point except newline */
unicode_letter = [\p{Lu}\p{Ll}\p{Lt}\p{Lm}\p{Lo}] /* a Unicode code point classified as "Letter" */
unicode_digit  = [\p{Nd}] /* a Unicode code point classified as "Number, decimal digit" */

// https://cuelang.org/docs/references/spec/#letters-and-digits
letter        = [_\p{Lu}\p{Ll}\p{Lt}\p{Lm}\p{Lo}] // _ plus {unicode_letter}
decimal_digit = [0-9]
octal_digit   = [0-7]
hex_digit     = [0-9A-Fa-f]
binary_digit  = [01]
letter_digit  = [_\p{Lu}\p{Ll}\p{Lt}\p{Lm}\p{Lo}\p{Nd}] // extension: letter or digit

// https://cuelang.org/docs/references/spec/#commas
comma       = [,]

// https://cuelang.org/docs/references/spec/#identifiers
identifier  = ("#" | "_#")? {letter} {letter_digit}*

// https://cuelang.org/docs/references/spec/#letters-and-digits
decimal_lit = [1-9] ("_"? {decimal_digit})*
decimals    = {decimal_digit} ("_"? {decimal_digit})*
si_lit      = {decimals} ("." {decimals})? {multiplier}
              | "." {decimals} {multiplier}
binary_lit  = "0b" {binary_digit} ("_"? {binary_digit})*
hex_lit     = "0" [xX] {hex_digit} ("_"? {hex_digit})*
octal_lit   = "0o" {octal_digit} ("_"? {octal_digit})*
multiplier  = [KMGTP] "i"?

// https://cuelang.org/docs/references/spec/#decimal-floating-point-literals
// fixme the grammar has "decimal_lit", but that's already defined above
float_lit = {decimals} "." {decimals}? {exponent}?
            | {decimals} {exponent}
            | "." {decimals} {exponent}?
exponent  = [eE] [+-]? {decimals}

// https://cuelang.org/docs/references/spec/#string-and-byte-sequence-literals
escaped_char     = "\\" "#"* [abfnrtv/\\'\"]
byte_value       = {octal_byte_value} | {hex_byte_value}
octal_byte_value = "\\"  {octal_digit} {3}
hex_byte_value   = "\\x" {hex_digit} {2}
little_u_value   = "\\u" {hex_digit} {4}
big_u_value      = "\\U" {hex_digit} {8}
unicode_value    = {unicode_char} | {little_u_value} | {big_u_value} | {escaped_char}

interpolation_start = "\\("
interpolation_end = ")"

%state STRING_LITERAL
%state STRING_MULTILINE
%state BYTE_LITERAL
%state BYTES_MULTILINE
%state EXPRESSION

%%
<STRING_LITERAL> {
    "\""         { popState(); return DOUBLE_QUOTE_END; }
}
<BYTE_LITERAL> {
    "'"          { popState(); return SINGLE_QUOTE_END; }
    {byte_value} { return BYTE_VALUE; }
}
<STRING_MULTILINE> {
    // matching \n as newline token, because the closing triple-quote must come after it
    {newline}    { return NEWLINE; }
    "\"\"\""     { popState(); return MULTILINE_STRING_END; }
}
<BYTES_MULTILINE> {
    // matching \n as newline token, because the closing triple-quote must come after it
    {newline}    { return NEWLINE; }
    "'''"        { popState(); return MULTILINE_BYTES_END; }
}
<STRING_LITERAL, BYTE_LITERAL, STRING_MULTILINE, BYTES_MULTILINE> {
    {interpolation_start} { pushState(EXPRESSION); return INTERPOLATION_START; }
    // fixme decide if we want to lex whitespace in strings as unicode_value or whitespace, might be needed for in-string-content search and tokenizing
    {unicode_value}       { return UNICODE_VALUE; }
}

<EXPRESSION> {
    {interpolation_end}  { popState(); return INTERPOLATION_END; }
}

<YYINITIAL, EXPRESSION> {
    "package" | "import"
    | "for" | "in" | "if" | "let"
                     { return KEYWORD; } // for now, one token for all, https://cuelang.org/docs/references/spec/#keywords
    "null"           { return NULL_LIT; } // https://cuelang.org/docs/references/spec/#null
    "true" | "false" { return BOOL_LIT; } // fixme currently undefined in spec

    "_|_"            { return BOTTOM; }

    // operator tokens
    "{"     { return LEFT_CURLY; }
    "}"     { return RIGHT_CURLY; }
    ":"     { return COLON; }
    "..."   { return ELLIPSIS_TOKEN; }
    ","     { return COMMA; }
    "="     { return EQ; }
    "?"     { return QMARK; }
    "@"     { return AT; }
    "["     { return LEFT_BRACKET; }
    "]"     { return RIGHT_BRACKET; }
    "("     { return LEFT_PAREN; }
    ")"     { return RIGHT_PAREN; }

    "!=" | "<" | "<=" | ">" | ">=" | "=~" | "!~"
            { return REL_OP; }
    "+" | "-"
            { return ADD_OP; }
    "*" | "/" | "div" | "mod" | "quo" | "rem"
            { return MUL_OP; }
    "|"     { return OP_DISJUNCTION; }
    "&"     { return OP_UNIFICATION; }
    "||"    { return OP_OR; }
    "&&"    { return OP_AND; }
    "=="    { return OP_EQ; }
    // end of operators

    "?"     { return QMARK; }
    "!"     { return EXCL; }
    "."     { return DOT; }

    {identifier}    { return IDENTIFIER; }

    {float_lit}     { return FLOAT_LIT; }
    {decimal_lit}
    | {si_lit}
    | {octal_lit}
    | {binary_lit}
    | {hex_lit}     { return INT_LIT; }
    "0" // fixme temporary change, "0" seems to be missing in the lang spec
                    { return INT_LIT; }

    "\""                   { pushState(STRING_LITERAL); return DOUBLE_QUOTE; }
    "\"\"\"" / {newline}   { pushState(STRING_MULTILINE); return MULTILINE_STRING_START; }
    "'"                    { pushState(BYTE_LITERAL); return SINGLE_QUOTE; }
    "'''" / {newline}      { pushState(BYTES_MULTILINE); return MULTILINE_BYTES_START; }

    "//" {unicode_char}*   { return COMMENT; }
}

{WHITE_SPACE_NEWLINE}   { return WHITE_SPACE_NEWLINE; }
{WHITE_SPACE}           { return WHITE_SPACE; }
[^]                     { return BAD_CHARACTER; }
