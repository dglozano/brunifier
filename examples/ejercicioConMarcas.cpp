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
int main(int argc, char *argv[]) {
float cordenadax, cordenaday;
int t=0,o[10]={0};
cout<<"ingrese el punto en el plano carteciano"<<endl;
cin>>cordenadax;
cin>>cordenaday;
if (cordenadax>0 && cordenaday>0) {
cout<<"el punto est� en el primer cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax>0 && cordenaday>0) EN LINEA 17
else {
if (cordenadax<0 && cordenaday>0) {
cout<<"el punto est� en el segundo cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax<0 && cordenaday>0) EN LINEA 21
else {
if (cordenadax<0 && cordenaday<0) {
cout<<"el punto est� en el tercer cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax<0 && cordenaday<0) EN LINEA 25
else {
if (cordenadax>0 && cordenaday<0) {
cout<<"El punto est� en el cuarto cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax>0 && cordenaday<0) EN LINEA 29
else {
cout<<"es el origen"<<endl;
switch(t) {
case 0: {
if(t==0) {
//hacer algo
while(t<5) {
t++;
}//CIERRA EL BLOQUE DE while(t<5) EN LINEA 38
switch(t+1) {
case 0: break;
default: {
break;
}//CIERRA EL BLOQUE DE default EN LINEA 43 DE switch(t+1) EN LINEA 41
}//CIERRA EL BLOQUE DE switch(t+1) EN LINEA 41
}//CIERRA EL BLOQUE DE if(t==0) EN LINEA 36
break;
}//CIERRA EL BLOQUE DE case 0 EN LINEA 35 DE switch(t) EN LINEA 34
case 1: {
break;
}//CIERRA EL BLOQUE DE case 1 EN LINEA 50 DE switch(t) EN LINEA 34
}//CIERRA EL BLOQUE DE switch(t) EN LINEA 34
}//CIERRA EL BLOQUE DE else EN LINEA 32 DE if (cordenadax>0 && cordenaday<0) EN LINEA 29
}//CIERRA EL BLOQUE DE else EN LINEA 28 DE if (cordenadax<0 && cordenaday<0) EN LINEA 25
}//CIERRA EL BLOQUE DE else EN LINEA 24 DE if (cordenadax<0 && cordenaday>0) EN LINEA 21
}//CIERRA EL BLOQUE DE else EN LINEA 20 DE if (cordenadax>0 && cordenaday>0) EN LINEA 17
cout<<t;
for(int i=0;i<10;i++) {
for(int j=0;j<10;j++) {
cout<<i;
cout<<i;
}//CIERRA EL BLOQUE DE for(int j=0;j<10;j++) EN LINEA 60
}//CIERRA EL BLOQUE DE for(int i=0;i<10;i++) EN LINEA 59
return 0;
}//CIERRA EL BLOQUE DE int main(int argc, char *argv[]) EN LINEA 11
int suma(int x,int y) {
int r;
r = x+y;
for(int j=0;j<10;j++) {
cout<<j;
}//CIERRA EL BLOQUE DE for(int j=0;j<10;j++) EN LINEA 70
do {
cout << r++ << endl;
} while(r<10);
return r;
}//CIERRA EL BLOQUE DE int suma(int x,int y) EN LINEA 67
