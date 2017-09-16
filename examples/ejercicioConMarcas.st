Object subclass: Persona [ "Comienzo de la clase Persona"


| nombre apellido dni |

proximo_DNI := 43000000.

Persona class >> new_nombre: un_nombre apellido: un_apellido [ "Comienzo de metodo de clase"


| instancia |

instancia := super new inicializar: proximo_DNI nombre: un_nombre apellido: un_apellido.

proximo_DNI := proximo_DNI + 1.

^instancia
] "Fin de metodo de clase new_nombre: un_nombre apellido: un_apellido"


inicializar: un_dni nombre: un_nombre apellido: un_apellido [ "Comienzo de metodo de instancia"

nombre := un_nombre.
apellido := un_apellido.
dni := un_dni.
] "Fin de metodo inicializar: un_dni nombre: un_nombre apellido: un_apellido"


nombre_completo [ "Comienzo de metodo de instancia"

^nombre , ' ' , apellido
] "Fin de metodo nombre_completo"


dni [ "Comienzo de metodo de instancia"

^dni.
] "Fin de metodo dni"

] "Fin de clase Persona"


p1 := Persona new_nombre: 'Juan' apellido: 'Perez'.
p2 := Persona new_nombre: 'Andr�s' apellido: 'Gonz�lez'.
p1 nombre_completo printNl.
p2 dni printNl.
