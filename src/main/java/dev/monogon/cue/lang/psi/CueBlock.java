package dev.monogon.cue.lang.psi;

/**
 * A block is a possibly empty sequence of declarations.
 * https://github.com/cuelang/cue/blob/master/doc/ref/spec.md#blocks
 */
public interface CueBlock extends CueCompositeElement {
    default boolean isValidBlock() {
        return true;
    }
}
