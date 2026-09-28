/* Construiremos árboles en Prolog basándonos en las lecciones 4 y 5 */

/*
	Los árboles se definen por la relación entre nodos padres y nodos hijos.
	Existen árboles que tienen hijos (ramas) y los árboles que no tienen ya ningún
	hijo (hojas).
*/

/* Primero crearemos el operador de "X lleva_a Y" para manifestar esa relación*/

:- op(800, xfx, lleva_a).

/* Ahora, sin mucho esfuerzo, podemos empezar a definir nuestro árbol */

/* Haremos el del siguiente esquema:

a es el nodo raíz y lo recorreremos de izquiera a derecha

  b   f
 /   /
a   d -- h
 \ / \
  c   g
   \
    e

*/

a lleva_a b.
a lleva_a c.
c lleva_a d.
c lleva_a e.
d lleva_a f.
d lleva_a g.
d lleva_a h.

/* Con esto ya habremos construido nuestro árbol */

/*
	Ahora viene la mejor parte. Tenemos que poder navegar por este árbol. Para ello,
	necesitaremos implementar algunas funciones.

	"X viene_de Y"
	"X tiene_raiz Y"

	Por ejemplo.
 */

:- op(800, xfx, viene_de).
:- op(800, xfx, tiene_raiz).

/*
	Estos operadores no son -autónomos- en tanto que una vez "a lleva_a b"
	ya sabemos que "b viene_de a". Sucede lo mismo con la raíz de todo el árbol.
*/

X viene_de Y :-
	Y lleva_a X. 		/* Relación en espejo */

/* Caso base */
X tiene_raiz Y :-
	Y lleva_a X,		/* X viene de Y */
	\+ (Y viene_de _). 	/* Y no tiene padre */

/* Caso resursivo */
X tiene_raiz Y :-
	Padre lleva_a X,	/* X viene de un Padre */
	Padre tiene_raiz Y. /* Padre viene de algo más arriba*/

/*
	Ejercicios:
	1. Diseñar la función de "son_hermanos" cuando dos nodos comparten raíz.
	2. Diseñar la función de "comparten_ancestro(N, X, Y)" donde
		X y Y son hojas y N es el nivel de ancestro anterior. (1 padre, 2 abuelo...)
	3. Diseñar la función que liste las hojas de las ramas hijas
		(recordar [Cola|Cabeza]).
	4. Diseñar la función que liste las ramas.
*/

/* Solución en la línea 200 */
/* 1. Hermanos: dos nodos comparten el mismo padre */
son_hermanos(X, Y) :-
    Padre lleva_a X,
    Padre lleva_a Y,
    X \= Y.


/* 2. Comparten ancestro
   N = nivel del ancestro:
   N = 1 -> padre
   N = 2 -> abuelo
   N = 3 -> bisabuelo
*/

comparten_ancestro(1, X, Y) :-
    Padre lleva_a X,
    Padre lleva_a Y,
    X \= Y.

comparten_ancestro(N, X, Y) :-
    N > 1,
    Padre lleva_a X,
    Padre lleva_a PadreX,
    Padre lleva_a Y,
    X \= Y,
    N2 is N - 1,
    comparten_ancestro(N2, PadreX, Y).


/* 3. Lista de hojas de las ramas hijas */

/* Una hoja es un nodo que no tiene hijos */
es_hoja(X) :-
    \+ (X lleva_a _).


/* Lista todas las hojas que descienden de X */
hojas(X, [X]) :-
    es_hoja(X).

hojas(X, Lista) :-
    X lleva_a Hijo,
    hojas(Hijo, ListaHijo),
    hojas_restantes(X, Hijo, ListaResto),
    append(ListaHijo, ListaResto, Lista).


/* Busca las hojas de los demás hijos */
hojas_restantes(Padre, HijoActual, Lista) :-
    Padre lleva_a Hijo,
    Hijo \= HijoActual,
    hojas(Hijo, ListaHijo),
    hojas_restantes(Padre, Hijo, ListaResto),
    append(ListaHijo, ListaResto, Lista).

hojas_restantes(Padre, HijoActual, []) :-
    \+ (Padre lleva_a Hijo),
    HijoActual = HijoActual.


/* 4. Listar las ramas */

rama(X, Y) :-
    X lleva_a Y.

rama(X, Y) :-
    X lleva_a Z,
    rama(Z, Y).


/* Lista de todas las ramas */
lista_ramas(Lista) :-
    findall(X-Y, rama(X, Y), Lista).