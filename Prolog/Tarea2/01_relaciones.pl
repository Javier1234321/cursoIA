/* Introducción */

/*
	Prolog es un lenguaje de programación lógico, esto quiere decir que 
	nosotros no ejecutaremos una a una las líneas de código que introduzcamos
	sino se declararemos una serie de relaciones lógicas.

	Estas relaciones lógicas serán verdaderas cuando Prolog encuentre alguna
	manera de satisfacer esas relaciones a partir de la información que le
	hayamos brindado. 
	
	Para establecer una relación entre diversos objetos se usa la
	siguiente sintaxis:

	relacion(cosa1, cosa2 ... cosaN). <-(Ojo: no olvidar el punto)
	
	la relación no tiene ningún orden en particular, está sujeta a nuestra
	interpretación únicamente, es por eso que debemos congruentes con
	nuestra notación.
	
	Las variables todas deben iniciar con mayúsucla y los nodos "declarados" 
	son con minúscula
*/

padre(jose, jesus).
madre(maria, jesus).

/* 
	Podemos pensar en este tipo de relaciones como una función de dos entradas
	esta función siempre llevará a falso -a menos- de que cumpla las
	reglas que nosotros hayamos establecido
	i.e.
	f(x, y). 				-> verdadero	
	padre(jose, jesus). 	-> verdadero
	
    ahora que ya determinamos que Jose y Maria son los padres de jesus,
    tenemos que determinar que Jesus es -hijo- de José y de María
*/

hijo(jesus, maria).
hijo(jesus, jose).
hijo(maria,ana).
hijo(jesus,maria).
/*
	hay que recordar que estaremos leyendo estos predicados como si fueran
	funciones: 
	g(X, Y) 				-> verdadero
	hijo(jesus, maria) 		-> verdadero
	
	podemos tener predicados con menos argumentos o más, por ejemplo...
*/

caliente(te).
caliente(cafe).
caliente(atole).

frio(helado).
frio(raspado).
frio(boli).

/*
	Así podremos preguntar si un boli es frío "frio(boli)." y obtendremos
	la respuesta natural verdadera "true".
	podemos establecer relaciones compuestas, por ejemplo...
	nuestras abuelas son nuestras abuelas, porque son madres de nuestros padres
	podemos definir esta relación de la siguiente manera:

	Para especificar este "Y (AND)" se utiliza una coma entre cada condición.
	
	abuela(X, Z) :-		/* para que X sea abuela de Z, para ello...	*/
	madre(X, Y),	/* solicitamos que X sea madre de Y		*/
	madre(Y, Z).    /* solicitamos que Y sea madre de Z		*/

	Las letras son "variables" que no han sido unificadas. Prolog hará lo posible
	por darnos algo f(X, Y) = "true" por lo que podemos introducir, ya sea 
	la X o la Y y prolog hará lo posible por hacer de esa relación verdadera.

	A pesar de que ya declaramos la relación de "maria" y "jose" como los
	pronitores de jesus, podemos ser un poco más específicos en qué significa
	eso. Para ello, podemos definir la relación así como lo hicimos con "abuela".
*/

/* Recordar que las mayúsculas indican variables, no valores */
progenitores(Madre, Padre, Hijo):-
	madre(Madre, Hijo),			/* solicitamos que Madre sea madre de Hijo */
	padre(Padre, Hijo). 		/* solicitamos que Padre sea padre de Hijo */

progenitores(maria, jose, jesus).

/*
	Hay que recordarque esto sigue siendo una función.
	f(X, Y) -> verdadero
	y será verdadero si y solo si se cumplen las relaciones tras el ":-"
	También tenemos una abuela paterna, afortunadamente, podemos
	tener ambas relaciones con el mismo nombre, y será durante la evaluación
	que el propio Prolog distinguirá ambos casos. Así que en el mismo código
	podemos agregar la abuela paterna:
*/

abuela(X, Z):-		/* para que X sea abuela de Z, para ello... */
	madre(X, Y),	/* solicitamos que X sea madre de Y		*/
	padre(Y, Z).    /* solicitamos que Y sea padre de Z		*/

/* podemos ahora marcar a Ana como abuela de Jesus: */

abuela(ana, jesus).

/* 
    Ejercicios:
	 Usando Swi-Prolog introduzca los siguientes comandos y concluya 
	 		- "frio(X)"				*presione "TAB" para iterar 
			- "caliente(B)"			*presione "TAB" para iterar
			- "padre(X, jesus)"
			- "abuela(A, jesus)"
			- "abuela(ana, X)"
	 
	 ¿Cómo sería la relación de un nieto?
	 ¿Cómo sería la relación entre Jose y Maria?
	 ¿Qué significaría "relacion(X, X)." ?
	 
	Nota: lo que ahí llamamos una relación, de manera más genérica se llamará
	un "predicado".
*/

/*Relacion de un nieto*/
nieto(X,Y):-
    hijo(X,Z),
    hijo(Z,Y).



/*Relacion de maria y jose*/
esposo(jose,maria).
esposo(maria,jose).

matrimonio(X,Y):-
    esposo(X,Y),
    esposo(Y,X).


/*La relacion(X,X) significa que la variable X se relaciona consigo misma*/