// the parser accepts keywords as name references, the lexer is using KEYWORD here to remain close to the spec

// Unless noted otherwise, they can also be used as identifiers to refer to the same name.
{name: package}
{name: import}
{name: for}
{name: in}
{name: if}
{name: let}

// predeclared identifiers are valid identifier names and values
{
    let: "value"
    number: let
    float: number
    string: float
    bytes: string
    uint: number
    rune: uint
    float64: rune
}

// These can never be used to refer to a field of the same name. This restriction is to ensure compatibility with JSON configuration files.
{name: null}
{name: true}
{name: false}