Object subclass: Persona [

	| nombre apellido dni |

	proximo_DNI := 43000000.

	Persona class >> new_nombre: un_nombre apellido: un_apellido [

		| instancia |
		
		instancia := super new inicializar: proximo_DNI nombre: un_nombre apellido: un_apellido.
		
		proximo_DNI := proximo_DNI + 1.
		
		^instancia
	]

	inicializar: un_dni nombre: un_nombre apellido: un_apellido [
		nombre := un_nombre.
		apellido := un_apellido.
		dni := un_dni.
	]

	nombre_completo [
		^nombre , ' ' , apellido
	]

	dni [
		^dni.
	]
]

p1 := Persona new_nombre: 'Juan' apellido: 'Perez'.
p2 := Persona new_nombre: 'Andrés' apellido: 'González'.
p1 nombre_completo printNl.
p2 dni printNl.
