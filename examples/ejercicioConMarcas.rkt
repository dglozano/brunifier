(define (armarMatriz m n) ; CIERRA armarMatriz EN define
(cortarCada (armarVector (* m n) ; CIERRA * EN armarVector
) ; CIERRA armarVector EN cortarCada
m n) ; CIERRA cortarCada EN define
) ; CIERRA define
(define (armarVector m) ; CIERRA armarVector EN define
(if (eq? m 0) ; CIERRA eq? EN  CONDICION DE if
'() ; CIERRA LISTA
(append (armarVector (- m 1) ; CIERRA - EN armarVector
) ; CIERRA armarVector EN append
(list m) ; CIERRA list EN append
) ; CIERRA append EN )
) ; CIERRA ) EN  CAMINO POR VERDADERO DE if
) ; CIERRA if EN define
(define (cortarCada v m n) ; CIERRA cortarCada EN define
(if (eq? m 1) ; CIERRA eq? EN  CONDICION DE if
(list v) ; CIERRA list EN  CAMINO POR VERDADERO DE if
(append (list (primeros n v) ; CIERRA primeros EN list
) ; CIERRA list EN append
(cortarCada (sacar n v) ; CIERRA sacar EN cortarCada
(- m 1) ; CIERRA - EN cortarCada
n) ; CIERRA cortarCada EN append
) ; CIERRA append EN  CAMINO POR FALSO DE if
) ; CIERRA if EN define
) ; CIERRA define EN define
(define (primeros n v) ; CIERRA primeros EN define
(if (eq? n 1) ; CIERRA eq? EN  CONDICION DE if
(cons (car v) ; CIERRA car EN cons
'() ; CIERRA LISTA
) ; CIERRA ) EN cons
(cons (car v) ; CIERRA car EN cons
(primeros (- n 1) ; CIERRA - EN primeros
(cdr v) ; CIERRA cdr EN primeros
) ; CIERRA primeros EN cons
) ; CIERRA cons EN cons
) ; CIERRA cons EN  CAMINO POR VERDADERO DE if
) ; CIERRA if EN define
(define (sacar n v) ; CIERRA sacar EN define
(if (eq? n 1) ; CIERRA eq? EN  CONDICION DE if
(cdr v) ; CIERRA cdr EN  CAMINO POR VERDADERO DE if
(sacar (- n 1) ; CIERRA - EN sacar
(cdr v) ; CIERRA cdr EN sacar
) ; CIERRA sacar EN  CAMINO POR FALSO DE if
) ; CIERRA if EN define
) ; CIERRA define EN define
