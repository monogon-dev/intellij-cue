<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# IntelliJ CUE Language Support Changelog

## [Unreleased]

### Added

- Support formatting with `cue fmt`. Only the reformatting of a complete file is supported.
- Application settings to configure a custom path to the `cue` binary. By default, the binary is searched in `$PATH`.
- Language injection into single-line and multi-line string literal. Interpolations are marked as read-only when opening the fragment editor.

### Changed

### Fixed

- [#35](https://github.com/nexantic/intellij-cue/issues/35): Trailing bracket on ellipsis multi-line list not parsed correctly.
- [#36](https://github.com/nexantic/intellij-cue/issues/36): Don't accept implicit comma separator in list literals.

## [0.6.0]

- Added: Automatically insert closing quotes when typing the opening quote, available for simple and multiline literals.
- Added: Support folding for CUE elements (file header, imports, import groups, structs, lists, interpolations, multiline strings,
  attributes).
- Added: Settings to control the default folding state of CUE elements.
- Fix [#27](https://github.com/nexantic/intellij-cue/issues/27), file starting with attribute raised as invalid
- Added: Highlighting setting for attributes.

## [0.5.0]

- Support variable escape prefix of the language specification.
- Support commenting lines via shortcut or action.
- Support highlighting of brace pairs in the editor.
- Fix parsing of function calls in interpolations.

## [0.4.0]

- Color scheme settings to configure CUE highlighting
- Added Highlighting setting for optional field names

## [0.3.1]

- Improved support for the CUE grammar
- Improved highlighting

## [0.2.1]

- First public release!
- Basic support for parsing and syntax highlighting.
