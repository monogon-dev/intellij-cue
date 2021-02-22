#MyStruct: {
    sub: field:    string
}

#MyStruct: {
    sub: enabled?: bool
}

myValue: #MyStruct & {
    sub: feild:   2     // error, feild not defined in #MyStruct
    sub: enabled: true  // okay
}

#D: {
    #OneOf

    c: int // adds this field.
}

#OneOf: { a: int } | { b: int }


D1: #D & { a: 12, c: 22 }  // { a: 12, c: 22 }
D2: #D & { a: 12, b: 33 }  // _|_ // cannot define both `a` and `b`


#A: {a: int}

B: {
    #A
    b: c: int
}

x: B
x: d: 3  // not allowed, as closed by embedded #A

y: B.b
y: d: 3  // allowed as nothing closes b

#B: {
    #A
    b: c: int
}

z: #B.b
z: d: 3  // not allowed, as referencing #B closes b