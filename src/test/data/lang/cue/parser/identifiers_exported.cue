package mypackage

foo:   string  // visible outside mypackage
"bar": string  // visible outside mypackage

#Foo: {      // visible outside mypackage
    a:  1    // visible outside mypackage
    _b: 2    // not visible outside mypackage

    #C: {    // visible outside mypackage
        d: 4 // visible outside mypackage
    }
    _#E: foo // not visible outside mypackage
}