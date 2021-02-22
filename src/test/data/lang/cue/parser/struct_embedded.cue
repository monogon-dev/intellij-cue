S1: {
    a: 1
    b: 2
    {
        c: 3
    }
}
// S1 is { a: 1, b: 2, c: 3 }

S2: close({
    a: 1
    b: 2
    {
        c: 3
    }
})
// same as close(S1)

S3: {
    a: 1
    b: 2
    close({
        c: 3
    })
}
// same as S2