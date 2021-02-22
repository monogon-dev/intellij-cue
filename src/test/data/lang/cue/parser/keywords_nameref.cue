package myPackage

package: {name:"package"}
import: {name:"import"}
for: {name:"for"}
in: {name:"in"}
if: {name:"if"}
let: {name:"let"}

// Unless noted otherwise, they can also be used as identifiers to refer to the same name.
a:{name: package}
b:{name: import}
c:{name: for}
e:{name: in}
f:{name: if}
g:{name: let}

// predeclared identifiers are valid identifier names and values
h: {
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
i: {name: null}
j: {name: true}
k: {name: false}