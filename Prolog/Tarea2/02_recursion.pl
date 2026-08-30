/* Funciones recursivas */

/*
	En Prolog, la recursión es mucho más fácil de implementar que los ciclos.
	En realidad, la recursión es la manera recomendada de hacer las cosas.
	Podemos plantear escenarios recursivos utilizando la siguiente sintaxis
*/

/* Ejemplo con Fibonacci */

/* Caso base */
fib(0, 0).
fib(1, 1).

/* Caso recursivo */
fib(X, Y):-				/* OJO: el comando "is" se usa para evaluar SOLO matemáticas. */
	X > 1,				/* Determinamos que X > 1, sino será falso */
	X1 is X - 1,		/* Determinamos que X1 = X - 1 */
	X2 is X - 2, 		/* Determinamos que X2 = X - 2 */
	fib(X1, Y1),		/* Obtenemos el fibonacci del valor anterior */
	fib(X2, Y2),		/* Obtenemos el fibonacci de dos valores atrás */
	Y is Y1 + Y2. 		/* Determinamos que Y = Y1 + Y2 */

/* Podemos probar la función en swipl como "fib(3, Y)." */	

/* Ejemplo con el factorial */

/* Como caso base dictamos un hecho, el factorial de 0 es 1 */
factorial(0, 1).

/* Ahora tenemos de crear la relación ó predicado recursivo */
factorial(X, Y):-
	X > 0,
	X1 is X - 1, 
	factorial(X1, Y1),		/* obtenemos el valor factorial de X - 1 en Z */
	Y is X * Y1.

/* 
	Las listas son una de las estructuras más comunes en Prolog por 
	la facilidad que hay de recorrerlas re manera recursiva.

	Su forma es:

	Nodo -> Nodo -> Nodo -> ... -> Nodo -> Nodo

	En nuestro caso, los nodos serán siempre una relación, como ya 
	lo hicimos en la lección anterior. Recordemos

	Abuela -> Madre -> Hijo
	Abuela -> Padre -> Hijo

	En caso de que quisiéramos tener una cadena más larga, ya no 
	tendríamos manera de referirnos a la -tatara tatara tatara ... abuela-
	Es por ello que tendremos que recorrer estas cadenas con una relación
	recursiva.

	Pensemos:

	Ancestro -> Ancestro -> Ancestro -> ... -> Ancestro -> Descendiente

	Hay cierta información que nos nodos. Por ejemplo, quién comenzó el 
	linaje y quién es el último descendiente del linaje. 

	(Podríamos pensar en los presidentes de un país)

	victoria -> guerrero -> ... -> calderon -> penna -> amlo -> sheinbaum

	Si preguntamos -quién es el último sucesor de "porfirio_diaz"-
	¿ćomo podríamos decir que es "sheinbaum"?
	Si preguntamos -quién es el primero en la cadena de "penna"-
	¿cómo podríamos decir que fue "victoria" (guadalupe)?

*/
/* 
	Ejercicios:
	1. ¿Cómo se podría usar la recursión para seguir una cadena así?
	1.1 Implemente una cadena usando una relación de sucesión
	2. Recórrala, desde el primer ancestro hasta el último descendiente.
	2.1 Ahora recórrala desde -cualquier- ancestro. (No se necesita más código).
	3. Recórrala, desde el último descendiente hasta el primero. 
	3.1 Ahora recórrala desde -cualquier- sucesor. (No se necesita más código).
	4. En un arbol genialógico, ¿podríamos encontrar cualesquiera dos parientes
		usando esta metodología?
	5. ¿Piense cómo se implementaría un arbol genialógico? 
		(Lo implementaremos más adelante)
*/

/* Pista */

/*
	sucesor(primero, segundo).
	sucesor(segundo, tercero).
	... etc. ...

	/* caso de hijo sin nieto */
	ultimo_sucesor(X, Y):-
		sucesor(X, Y),
        \+ sucesor(Y, _).       /* aquí marcamos que "Y" NO es padre de nadie
                                "_" denota un valor que no nos importa */


	/* caso de hijo con nieto */ 
	ultimo_sucesor(X, Z):-
		.... aquí va algo ...
*/

/* Solución en la línea 200 */
% =========================================
% 1. CADENA DE SUCESORES
% =========================================

sucesor(victoria, guerrero).
sucesor(guerrero, madero).
sucesor(madero, carranza).
sucesor(carranza, obregon).
sucesor(obregon, calles).
sucesor(calles, cardenas).
sucesor(cardenas, avila_camacho).
sucesor(avila_camacho, aleman).
sucesor(aleman, ruiz_cortines).
sucesor(ruiz_cortines, lopez_mateos).
sucesor(lopez_mateos, diaz_ordaz).
sucesor(diaz_ordaz, echeverria).
sucesor(echeverria, lopez_portillo).
sucesor(lopez_portillo, de_la_madrid).
sucesor(de_la_madrid, salinas).
sucesor(salinas, zedillo).
sucesor(zedillo, fox).
sucesor(fox, calderon).
sucesor(calderon, pena).
sucesor(pena, amlo).
sucesor(amlo, sheinbaum).


% =========================================
% 2. ÚLTIMO SUCESOR
% =========================================

ultimo_sucesor(X, Y) :-
    sucesor(X, Y),
    \+ sucesor(Y, _).

ultimo_sucesor(X, Z) :-
    sucesor(X, Y),
    ultimo_sucesor(Y, Z).


% =========================================
% 3. RECORRER DE PRIMERO A ÚLTIMO
% =========================================

recorrer(X) :-
    writeln(X),
    sucesor(X, Y),
    recorrer(Y).

recorrer(X) :-
    \+ sucesor(X, _),
    writeln(X).


% =========================================
% 4. RELACIÓN INVERSA
% =========================================

anterior(X, Y) :-
    sucesor(Y, X).


% =========================================
% 5. RECORRER DE ÚLTIMO A PRIMERO
% =========================================

recorrer_reversa(X) :-
    writeln(X),
    anterior(X, Y),
    recorrer_reversa(Y).

recorrer_reversa(X) :-
    \+ anterior(X, _),
    writeln(X).


% =========================================
% 6. ÁRBOL GENEALÓGICO
% =========================================

padre(juan, pedro).
padre(pedro, jose).
padre(jose, miguel).

madre(ana, pedro).
madre(maria, jose).
madre(lucia, miguel).

progenitor(X, Y) :-
    padre(X, Y).

progenitor(X, Y) :-
    madre(X, Y).

ancestro(X, Y) :-
    progenitor(X, Y).

ancestro(X, Y) :-
    progenitor(X, Z),
    ancestro(Z, Y).































































































































/*
/* Linage de la casa carolingia */
padre(martel, pipino).
padre(pipino, carlomagno).
padre(carlomagno, ludovico).
padre(ludovico, lotario).

ultimo_descendiente(X, Z):-
	padre(X, Y),
	ultimo_descendiente(Y, Z).

ultimo_descendiente(X, Y):-
	padre(X, Y),
	\+ padre(Y, _).
*/