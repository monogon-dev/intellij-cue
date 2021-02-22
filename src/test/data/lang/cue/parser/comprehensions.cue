a: [1, 2, 3, 4]
b: [ for x in a if x > 1 { x+1 } ]  // [3, 4, 5]

c: {
    for x in a
    if x < 4
    let y = 1 {
        "\(x)": x + y
    }
}
d: { "1": 2, "2": 3, "3": 4 }