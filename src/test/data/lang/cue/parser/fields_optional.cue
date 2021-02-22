a: { foo?: string }
b: { foo: "bar" }
c: { foo?: *"bar" | string }

d: a & b
e: b & c
f: a & c
g: a & { foo?: number }
h: b & { foo?: number }
i: c & { foo: string }

intMap: [string]: int
intMap: {
    t1: 43
    t2: 2.4  // error: 2.4 is not an integer
}

nameMap: [string]: {
    firstName: string
    nickName:  *firstName | string
}

nameMap: hank: { firstName: "Hank" }