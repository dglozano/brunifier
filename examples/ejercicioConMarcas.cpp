#include <iostream>
using namespace std;
struct Alumno {
int j;
int p;
float c;
};//CIERRA LA DEFINICIÓN DE struct Alumno
struct pepe {
	float c;
};//CIERRA LA DEFINICIÓN DE struct pepe
int main(int argc, char *argv[]) {
float cordenadax, cordenaday;
int t=0,o[10]={0};
cout<<"ingrese el punto en el plano carteciano"<<endl;
cin>>cordenadax;
cin>>cordenaday;
if (cordenadax>0 && cordenaday>0) {
cout<<"el punto está en el primer cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax>0 && cordenaday>0)
else {
if (cordenadax<0 && cordenaday>0) {
cout<<"el punto está en el segundo cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax<0 && cordenaday>0)
else {
if (cordenadax<0 && cordenaday<0) {
cout<<"el punto está en el tercer cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax<0 && cordenaday<0)
else {
if (cordenadax>0 && cordenaday<0) {
cout<<"El punto está en el cuarto cuadrante"<<endl;
}//CIERRA EL BLOQUE DE if (cordenadax>0 && cordenaday<0)
else {
cout<<"es el origen"<<endl;
switch(t) {
case 0: {
if(t==0) {
//hacer algo
switch(t+1) {
case 0: break;
default: {
break;
}//CIERRA EL BLOQUE DE default DE switch(t+1)
}//CIERRA EL BLOQUE DE switch(t+1)
}//CIERRA EL BLOQUE DE if(t==0)
break;
}//CIERRA EL BLOQUE DE case 0 DE switch(t)
case 1: {
break;
}//CIERRA EL BLOQUE DE case 1 DE switch(t)
}//CIERRA EL BLOQUE DE switch(t)
}//CIERRA EL BLOQUE DE else DE if (cordenadax>0 && cordenaday<0)
}//CIERRA EL BLOQUE DE else DE if (cordenadax<0 && cordenaday<0)
}//CIERRA EL BLOQUE DE else DE if (cordenadax<0 && cordenaday>0)
}//CIERRA EL BLOQUE DE else DE if (cordenadax>0 && cordenaday>0)
cout<<t;
for(int i=0;i<10;i++) {
for(int j=0;j<10;j++) {
cout<<i;
cout<<i;
}//CIERRA EL BLOQUE DE for(int j=0;j<10;j++)
}//CIERRA EL BLOQUE DE for(int i=0;i<10;i++)
return 0;
}//CIERRA EL BLOQUE DE int main(int argc, char *argv[])
int suma(int x,int y) {
int r;
r = x+y;
for(int j=0;j<10;j++) {
cout<<j;
}//CIERRA EL BLOQUE DE for(int j=0;j<10;j++)
return r;
}//CIERRA EL BLOQUE DE int suma(int x,int y)
