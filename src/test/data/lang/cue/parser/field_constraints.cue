{foo?: 3} & {foo: 3}
{foo!: 3} & {foo: 3}
{foo!: int} & {foo: int}
{foo!: int} & {foo?: <1}
{foo!: int} & {foo: <=3}
{foo!: int} & {foo: 3}
{foo!: 3} & {foo: int}
{foo!: 3} & {foo: <=4}
{foo?: 1} & {foo?: 2}
{foo?: 1} & {foo!: 2}
{foo?: 1} & {foo: 2}