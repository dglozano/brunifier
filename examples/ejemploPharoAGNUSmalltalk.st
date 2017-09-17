Object subclass: Cliente [

"Declaración de variables de instancia"
|ic nombre apellido dni numero plan numerosGratis llamadasRealizadas| 

duracionLlamadas [
^llamadasRealizadas inject: 0 into: [ :suma :llamada | suma + llamada duracion ]. 
] "final de método duracionLlamadas"

sos: icBuscado [
^ic = icBuscado. 
] "final de método sos:"

llamarA: clienteDestino durante: minutos [
^llamadasRealizadas add: (Llamada newDe: self para: clienteDestino durante: minutos). 
] "final de método llamarA:durante:"

datosRelevantes [
^{ nombre. apellido. dni. numero. plan nombre. }. 
] "final de método datosRelevantes"

calcularCostoLlamadas [
^plan calcularCostoLlamadasDe: self. 
] "final de método calcularCostoLlamadas"

numero [
^numero. 
] "final de método numero"

tenesGratisA: clienteDestino [
^numerosGratis includes: (clienteDestino numero). 
] "final de método tenesGratisA:"

llamadasRealizadas [
^llamadasRealizadas. 
] "final de método llamadasRealizadas"

inicializarIc: icInicio nombre: nombreInicio apellido: apellidoInicio dni: dniInicio numero: numeroInicio plan: planInicio [
numerosGratis := OrderedCollection new.
llamadasRealizadas := OrderedCollection new.
ic := icInicio.
nombre := nombreInicio.
apellido := apellidoInicio.
dni := dniInicio.
numero := numeroInicio.
plan := planInicio. 
] "final de método inicializarIc:nombre:apellido:dni:numero:plan:"

agregarNumeroGratis: numeroGratis [
((plan cantidadNumerosGratis) > (numerosGratis size)) ifTrue: [ ^numerosGratis add: numeroGratis ].
^self error: 'No puede agregar más números gratis'. 
] "final de método agregarNumeroGratis:"

Cliente class >> newIc: ic nombre: nombre apellido: apellido dni: dni numero: numero plan: plan [
^super new inicializarIc: ic nombre: nombre apellido: apellido dni: dni numero: numero plan: plan. 
] "final de método Cliente class >> newIc:nombre:apellido:dni:numero:plan:"

] "final de clase Cliente"


Object subclass: Gestor [

"Declaración de variables de instancia"
|clientes planes| 

costoLlamadas: ic [
^(self buscarCliente: ic) calcularCostoLlamadas. 
] "final de método costoLlamadas:"

buscarCliente: ic [
^clientes detect: [ :c | c sos: ic. ] ifNone: [ ^self error: 'No se encontró al cliente buscado' ] 
] "final de método buscarCliente:"

agregarA: ic numeroGratis: numeroGratis [
^(self buscarCliente: ic) agregarNumeroGratis: numeroGratis. 
] "final de método agregarA:numeroGratis:"

inicializar [
clientes := OrderedCollection new.
planes := OrderedCollection new. 
] "final de método inicializar"

llamadas: ic [
^(self buscarCliente: ic) duracionLlamadas.
 
] "final de método llamadas:"

cliente: ic [
^(self buscarCliente: ic) datosRelevantes. 
] "final de método cliente:"

agregarPlan: plan  [
planes add: plan. 
] "final de método agregarPlan:"

agregarCliente: cliente [
clientes add: cliente. 
] "final de método agregarCliente:"

crearLlamada: icOrig dest: icDest duracion: minutos [
| clienteOrigen clienteDestino |
clienteOrigen := self buscarCliente: icOrig.
clienteDestino := self buscarCliente: icDest.
^clienteOrigen llamarA: clienteDestino  durante: minutos. 
] "final de método crearLlamada:dest:duracion:"

Gestor class >> new [
^super new inicializar. 
] "final de método Gestor class >> new"

] "final de clase Gestor"


Object subclass: Llamada [

"Declaración de variables de instancia"
|clienteOrigen clienteReceptor duracion| 

duracion [
^duracion. 
] "final de método duracion"

destino [
^clienteReceptor. 
] "final de método destino"

inicializarDe: clienteO para: clienteD durante: minutos [
clienteOrigen := clienteO.
clienteReceptor := clienteD.
duracion := minutos. 
] "final de método inicializarDe:para:durante:"

Llamada class >> newDe: clienteO para: clienteD durante: minutos [
^super new inicializarDe: clienteO para: clienteD durante: minutos 
] "final de método Llamada class >> newDe:para:durante:"

] "final de clase Llamada"


Object subclass: Plan [

"Declaración de variables de instancia"
|nombre costoDelMinuto minutosLibres cantidadNumerosGratis| 

calcularCostoLlamadasDe: cliente [
|llamadasCobradas minutosCobrados|
llamadasCobradas := (cliente llamadasRealizadas) reject: [ :llamada | cliente tenesGratisA: (llamada destino) ].
minutosCobrados := (llamadasCobradas inject: 0 into: [ :suma :llamada | suma + llamada duracion ]) - minutosLibres.
(minutosCobrados <= 0)	ifTrue: [ ^0 ]
								ifFalse: [ ^minutosCobrados * costoDelMinuto ]. 
] "final de método calcularCostoLlamadasDe:"

cantidadNumerosGratis [
^cantidadNumerosGratis. 
] "final de método cantidadNumerosGratis"

inicializarNombre: nombreInicio costoMin: costoDelMinutoInicio minLibres: minutosLibresInicio cantNumGratis: cantidadNumerosGratisInicio [
nombre := nombreInicio.
costoDelMinuto := costoDelMinutoInicio.
minutosLibres := minutosLibresInicio.
cantidadNumerosGratis := cantidadNumerosGratisInicio. 
] "final de método inicializarNombre:costoMin:minLibres:cantNumGratis:"

nombre [
^nombre. 
] "final de método nombre"

Plan class >> newNombre: nombre costoMin: costoDelMinuto minLibres: minutosLibres cantNumGratis: cantidadNumerosGratis [
^super new inicializarNombre: nombre costoMin: costoDelMinuto minLibres: minutosLibres cantNumGratis: cantidadNumerosGratis. 
] "final de método Plan class >> newNombre:costoMin:minLibres:cantNumGratis:"

] "final de clase Plan"
