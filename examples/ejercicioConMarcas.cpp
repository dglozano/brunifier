#include <iostream>
using namespace std;
struct Alumno {
int j;
int p;
float c;
};//CIERRA LA DEFINICION DE struct Alumno EN LINEA 3
struct pepe {
	float c;
};//CIERRA LA DEFINICION DE struct pepe EN LINEA 8
void funcion()// FUNCION DEFINIDA ENTRE LAS LINEAS 11 Y 14
for(int i=0;i<5;i++)
cout << "Hola" << endl;
}//CIERRA EL BLOQUE DE void funcion() EN LINEA 11
int main(int argc, char *argv[])// FUNCION DEFINIDA ENTRE LAS LINEAS 15 Y 70
float cordenadax, cordenaday;
int t=0,o[10]={0};
cout<<"ingrese el punto en el plano carteciano"<<endl;
cin>>cordenadax;
cin>>cordenaday;
if (cordenadax>0 && cordenaday>0) {
cout<<"el punto est� en el primer cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax>0 && cordenaday>0) EN LINEA 21
else {
if (cordenadax<0 && cordenaday>0) {
cout<<"el punto est� en el segundo cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax<0 && cordenaday>0) EN LINEA 25
else {
if (cordenadax<0 && cordenaday<0) {
cout<<"el punto est� en el tercer cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax<0 && cordenaday<0) EN LINEA 29
else {
if (cordenadax>0 && cordenaday<0) {
cout<<"El punto est� en el cuarto cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax>0 && cordenaday<0) EN LINEA 33
else {
cout<<"es el origen"<<endl;
switch(t) {
case 0: {
if(t==0) {
//hacer algo
while(t<5) {
t++;
}//CIERRA EL BLOQUE DE while(t<5) EN LINEA 42
switch(t+1) {
case 0: break;
default: {
break;
}//CIERRA EL BLOQUE DE default EN LINEA 47 DE switch(t+1) EN LINEA 45
}//CIERRA EL BLOQUE DE switch(t+1) EN LINEA 45
}//CIERRA EL BLOQUE DE if(t==0) EN LINEA 40
break;
}//CIERRA EL BLOQUE DE case 0 EN LINEA 39 DE switch(t) EN LINEA 38
case 1: {
break;
}//CIERRA EL BLOQUE DE case 1 EN LINEA 54 DE switch(t) EN LINEA 38
}//CIERRA EL BLOQUE DE switch(t) EN LINEA 38
}//CIERRA EL BLOQUE DE else EN LINEA 36 DE if (cordenadax>0 && cordenaday<0) EN LINEA 33
}//CIERRA EL BLOQUE DE else EN LINEA 32 DE if (cordenadax<0 && cordenaday<0) EN LINEA 29
}//CIERRA EL BLOQUE DE else EN LINEA 28 DE if (cordenadax<0 && cordenaday>0) EN LINEA 25
}//CIERRA EL BLOQUE DE else EN LINEA 24 DE if (cordenadax>0 && cordenaday>0) EN LINEA 21
cout<<t;
for(int i=0;i<10;i++) {
for(int j=0;j<10;j++) {
cout<<i;
cout<<i;
}//CIERRA EL BLOQUE DE for(int j=0;j<10;j++) EN LINEA 64
}//CIERRA EL BLOQUE DE for(int i=0;i<10;i++) EN LINEA 63
return 0;
}//CIERRA EL BLOQUE DE int main(int argc, char *argv[]) EN LINEA 15
int suma(int x,int y)// FUNCION DEFINIDA ENTRE LAS LINEAS 71 Y 79
int r;
r = x+y;
for(int j=0;j<10;j++) {
cout<<j;
}//CIERRA EL BLOQUE DE for(int j=0;j<10;j++) EN LINEA 74
do {
cout << r++ << endl;
} while(r<10);
return r;
}//CIERRA EL BLOQUE DE int suma(int x,int y) EN LINEA 71
void funcion2(int x)// FUNCION DEFINIDA ENTRE LAS LINEAS 82 Y 84
cout << "funcion2" << x << endl;
}//CIERRA EL BLOQUE DE void funcion2(int x) EN LINEA 82
