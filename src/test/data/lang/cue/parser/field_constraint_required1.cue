#Def: {
    kind!: "def"
    intList!: [...int]
}

#Person: {
    name!: string
    age?:  int
}

jack: #Person & {age: 3}