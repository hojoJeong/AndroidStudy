
void main(){

  String? data = null;
  //?. 연산자
  var result = data?.length;
  print(result);  //null 출력됨



  //객체가 null이 아니면 길이를 null이면 0을 출력하도록
  String? data1 = null;

  var result1 = data1?.length ?? 0;
  print(result1); //0 출력


}




