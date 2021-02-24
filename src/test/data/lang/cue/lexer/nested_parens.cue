import "strings"

v: "NAME"
x: "\( strings.ToLower(v) )"
y: "\( strings.ToLower("\( string.toLower("ABC") )") )"