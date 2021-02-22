// An alias declaration
Alias = 3
a: Alias  // 3

// A field alias
foo: X  // 4
X="not an identifier": 4

// A label alias
[Y=string]: { name: Y }
foo: { value: 1 } // outputs: foo: { name: "foo", value: 1 }
