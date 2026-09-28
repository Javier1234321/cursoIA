/* Manejo de arreglos/cadenas */

/*
	En Prolog todos los objetos son nodos o arreglos de nodos. Los arreglos se
	expresan utilizando la notación [a, b, c, ... z].

	NOTA:
	Incidentalmente, estos arreglos también se llaman "cadenas", por lo que
	deberemos tener cuidado cuando hablemos de una cadena como una lista
	de objetos relacionados -en cadena- (como hicimos la lección anterior)
	y una -cadena de objetos-. Ya sea arreglo o cadena, sólo hay que ser
	claros al momento de hablar de ellos.

	Hay un operador especial que podemos utilizar con los arreglos, "decapitar".
	Con este operador especial obtendremos el primer elemento del arreglos.
	Para decapitar una cadena, se usa el símbolo "|".

	Pensemos que tuviéramos una arreglo/cadena cualquiera...
	"Cadena = [a, b, c, d]."
	Podemos -decapitar- la cadena y eso nos definirá una cabeza y una cola.
	"Cadena = [Cabeza | Cola]."

	Como la cadena ya se encontraba definida, Prolog hará lo posible para
	determinar esa -cabeza- (un nodo) y esa cola (el resto).
	En nuestro caso, deberíamos ver la cabeza y cola resultantes:
	"Cabeza : a"
	"Cola : [b, c, d]"
*/

/*
   Haciendo uso de la "decapitación" podemos manipular las cadenas a como nos
   de placer.
   En esta lección se tratarán algunas de las manipulaciones más sencillas y
   útiles que podemos realizar.

   Usaremos mucha recursión. (Es importantísimo que la repasen).
*/

/* Podemos voltear una cadena usando una relación */
/* voltea(Original, Acumulador, Resultado) 	*/

/*
	Si la cadena está vacía, ya no tenemos que acumular texto, entonces el
	acumulador y el resultado son iguales. Este será un caso base de un método
	recursivo.
*/

voltea([], Acum, Acum).

/* Sino, tenemos que decapitar la cadena y poner la cabeza al final */
voltea(Original, Acum, Res):-       /* OJO: el comando igual se usa SÓLO para unificar, NO EVALUAR */
	Original = [Cabeza | Cola],     /* Unificamos cabeza y cola decapitando la cadena original */
	AcumNuevo = [Cabeza | Acum],    /* Unificamos un nuevo acumulador concatenando en la cabeza */
	voltea(Cola, AcumNuevo, Res).   /* Y volteamos el resto del texto, la "cola". */

/*	Ejemplo: "voltea([a,b,c,d,e], [], Res)." */

/*
	Como en Prolog todo se trabaja como árboles de nodos, tenemos que solicitar
	que las cadenas de texto se trabajen como tal, no como texto. Aquí dejo un
	ejemplo de cómo convertir de texto a nodos, y de nodos a texto.
*/

voltea_texto(Texto, Volteado):-
	string_codes(Texto, Codigos),
	voltea(Codigos, [], CodigosVolteados),
	string_codes(Volteado, CodigosVolteados).

/* Ejemplo: "voltea_texto("asdfg", Res)." */

/*
	Ejercicios:

	1. Voltear la lista ligada elaborada en el ejercicio anterior como una cadena.
	1.1 Obtener la lista ligada -sin voltear- como una cadena
	2. ¿Cómo podríamos partir una cadena a la mitad?
	3. Elabore el código para partir una cadena a la mitad y regrese dos cadenas
	4. Elabore el código que regrese una cadena con ambas mitades (ya no regrese
		 las dos cadenas por separado).
	5. Elabore un código que parta una cadena en N cadenas más pequeñas y las
		 regrese.
*/


padre(a, b).
padre(b, c).
padre(c, d).
padre(d, e).

largo_lista(Primero, Contador, Largo):-
	... aquí va algo ...

largo_lista(Ultimo, Contador, Largo):-
	... aquí va algo...

recolecta_hasta_indice(Primero, Indice, Lista, Siguiente):-
	Indice > 0,
	... aquí va algo...

recolecta_hasta_indice(Primero, Indice, Lista, Siguiente):-
	Indice =< 0,					/* Ojo con esta sintaxis de menor o igual */
	... aquí va algo...

recolecta(Primero, Indice, Lista):-
	... aquí va algo...

recolecta(Ultimo, Indice, Lista):-
	... aquí va algo...

parte_lista_en_dos(Primero, PrimeraMitad, SegundaMitad):-
	largo_lista(Primero, 0, LargoOriginal),
	LargoMitad is LargoOriginal // 2,			/* división sin decimales */
	recolecta_hasta_indice(Primero, LargoMitad, [], PrimeraMitad, Siguiente),
	recolecta(Siguiente, [], SegundaMitad).

tiene_hijo(X):-
	padre(X, _). 		/* Podemos preguntar si X tiene un hijo, sin importar quién */

padre(a, b).
padre(b, c).
padre(c, d).
padre(d, e).

tiene_hijo(X):-
	padre(X, _).

largo_lista(Primero, Contador, Largo):-
	tiene_hijo(Primero),
	ContadorNuevo is Contador + 1,
	padre(Primero, Hijo),
	largo_lista(Hijo, ContadorNuevo, Largo).

largo_lista(Ultimo, Contador, Largo):-
	\+ tiene_hijo(Ultimo),
	ContadorNuevo is Contador + 1,
	Largo = ContadorNuevo.

recolecta(Primero, Acumulador, Lista):-
	tiene_hijo(Primero),
	AcumuladorNuevo = [Primero | Acumulador],
	padre(Primero, Hijo),
	recolecta(Hijo, AcumuladorNuevo, Lista).

recolecta(Ultimo, Acumulador, Lista):-
	\+ tiene_hijo(Ultimo),
	Lista = [Ultimo | Acumulador].

recolecta_hasta_indice(Primero, Indice, AcumLista, Lista, Siguiente):-
	Indice > 0,
	Indice1 is Indice - 1,
	AcumLista1 = [Primero | AcumLista],
	padre(Primero, Hijo),
	recolecta_hasta_indice(Hijo, Indice1, AcumLista1, Lista, Siguiente).

recolecta_hasta_indice(Primero, Indice, AcumLista, Lista, Siguiente):-
	Indice =< 0,
	Lista = [Primero | AcumLista],
	padre(Primero, Siguiente).


parte_lista_en_dos(Primero, PrimeraMitad, SegundaMitad):-
	largo_lista(Primero, 0, LargoOriginal),
	LargoMitad is LargoOriginal // 2,
	recolecta_hasta_indice(Primero, LargoMitad, [], PrimeraMitad, Siguiente),
	recolecta(Siguiente, [], SegundaMitad).




