/* Esta sección demuestra uno de los operadores más poderosos de Prolog */
/*
	En prolog, así como en otros idiomas existe la posibilidad de definir
	nuestros propios operadores. Para ello, haremos uso del comando op().
*/

/*
	Anteriormente habíamos definido las relaciones con la siguiente sintaxis:
	r(A, B).

	Sin embargo, si la relación se usa demasiado o se quiere expresar de una
	manera más amable a la vista, podemos utilizar un nuevo operador para
	expresar esa relación entre los objetos A y B.

	i.e.
	"padre(jose, jesus)" sería más fácil de leer como "jose es_padre_de jesus"
	es por eso que podemos definir el operador "es_padre_de" así como muchos otros

	Los únicos operadores que no debemos modificar son los previamente existenes,
	como los operadores matemáticos "+ - > < / //" etc. ya que sobreescribirlos
	podría alterar su comportamiento en casos más genéricos como la aritmética.

	Los operadores tienen jerarquía, así como los símbolos aritméticos. Hay que
	recordar que incluso los paréntesis y la coma tienen un orden de jerarquía.
	A la jerarquía también se llama "presedencia".

	Nos basaremos en la documentación...
	https://www.swi-prolog.org/pldoc/man?section=operators

	Veamos un ejemplo:
*/

/* Definimos es_padre_de como un operador */
/* Ojo que antes de "op" viene un ":-" */
:- op(400, xfx, es_padre_de).

/*
	Así, ya existirá la relación es_padre_de con sintaxis "X es_padre_de Y"
	pero detrás de cámaras Prolog lo seguirá tratando como "es_padre_de(X, Y)"
*/

jacobo es_padre_de jose.
jose es_padre_de jesus.

/* Podemos igualmente definir el operador de abuelo */


:- op(400, xfx, es_abuelo_de).

/*
	Y tenemos que recordar que el "abuelo" se define a partir de la relación
	de "X es padre del padre de Y"
*/

X es_abuelo_de Z:-
	X es_padre_de Y,
	Y es_padre_de Z.

/* Comprobar que jacobo sea abuelo de jesus con "jacobo es_abuelo_de jesus" */

/* Podemos definir los operadores como sufijo ("xf"), prefijo ("fx") ó infijo ("xfx"). */

/*
	Ejercicios:
	1. Definir el factorial con la letra "f" o sea, "X f Y".
	2. Definir el operador "es_feliz" con formato de sufijo.
	2.1 Probarlo en terminal.
	3. Definir el operador "no_quiero" con formato prefijo.
	3.1 Probarlo en terminal.
*/

/* Pista */

/*
:- op(900, xfx, f).
	0 f 1.
	X f Y :-
	... caso recursivo...
*/


/* Solución en la línea 200*/

/* 1. Factorial con el operador f */
:- op(900,xfx,f).
0 f 1.
X f Y:-
    X>0,
    X1 is X-1,
    X1 f Y1,
    Y is X*Y1.
/* 2. Operador es_feliz como sufijo */
:- op(900,xf,es_feliz).
juan es_feliz.
/* 3. Operador no_quiero como prefijo */
:- op(900,fx,no_quiero).

no_quiero(X):-
    \+ X.







