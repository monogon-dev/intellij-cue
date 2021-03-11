// implicit commas are not allowed in list literals
<info descr="null">a</info>: [
    "value"<EOLError descr="Missing ',' before newline in list literal"></EOLError>
    ...
]

<info descr="null">a</info>: [
    "a"<EOLError descr="Missing ',' before newline in list literal"></EOLError>
    "b"
]