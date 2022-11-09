
void calc(int a, int b){
  print(a + b);
}

// void main(){
//   var fun = calc;
//   fun(4,5);
// }


void calc2(Function(int a) f){
  f(20);
}

void main(){
  calc2((a){
    print(a);
  });
}


