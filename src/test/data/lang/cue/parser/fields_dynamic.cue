foo: {
	"x": "y"
}

s: {
	(b):                        "bar"
	(list.Slice(a.b, 0, 1)[0]): "x"
	(a.c[0]):                   "y"
}

for k, v in foo {
	(k): v
}

command: test: {
	for t in tests {
		(t.name): {
			print: cli.Print & {
				text: "testing \(t.name)"
			}
		}
	}
}
