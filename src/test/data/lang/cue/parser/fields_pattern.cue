a: {
    foo:    string  // foo is a string
    ["^i"]: int     // all other fields starting with i are integers
    ["^b"]: bool    // all other fields starting with b are booleans
    ...string       // all other fields must be a string
}

b: a & {
    i3:    3
    bar:   true
    other: "a string"
}