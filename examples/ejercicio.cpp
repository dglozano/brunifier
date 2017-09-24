#include <iostream>
using namespace std;
struct Alumno {
int j;
int p;
float c;
};
struct pepe {
	float c;
};
void funcion() {
for(int i=0;i<5;i++)
cout << "Hola" << endl;
}
int main(int argc, char *argv[]) {
float cordenadax, cordenaday;
int t=0,o[10]={0};
cout<<"ingrese el punto en el plano carteciano"<<endl;
cin>>cordenadax;
cin>>cordenaday;
if (cordenadax>0 && cordenaday>0) {
cout<<"el punto está en el primer cuadrante"<<endl;
}
else {
if (cordenadax<0 && cordenaday>0) {
cout<<"el punto está en el segundo cuadrante"<<endl;
}
else {
if (cordenadax<0 && cordenaday<0) {
cout<<"el punto está en el tercer cuadrante"<<endl;
}
else {
if (cordenadax>0 && cordenaday<0) {
cout<<"El punto está en el cuarto cuadrante"<<endl;
}
else {
cout<<"es el origen"<<endl;
switch(t) {
case 0: {
if(t==0) {
//hacer algo
while(t<5) {
t++;
}
switch(t+1) {
case 0: break;
default: {
break;
}
}
}
break;
}
case 1: {
break;
}
}
}
}
}
}
cout<<t;
for(int i=0;i<10;i++) {
for(int j=0;j<10;j++) {
cout<<i;
cout<<i;
}
}
return 0;
}
int suma(int x,int y) {
int r;
r = x+y;
for(int j=0;j<10;j++) {
cout<<j;
}
do {
cout << r++ << endl;
} while(r<10);
return r;
}
void funcion2(int x) {
cout << "funcion2" << x << endl;
}
