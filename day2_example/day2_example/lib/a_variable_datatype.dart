
/// document comment...
void test(){

  //문자열
  String name;
  name="SSAFY";

  int i = 10;
  double d = 10.0;

  //int와 double은 num 타입에 포함
  num a = 10;
  num b = 15.0;

  //다트에선 자동 형 변환 지원 안됨
  int num1 = 10;
  //double num2 = num1; //error

  //type casting 필요 --> 이건 오류 .!!!
  // double num3 = num1 as double;

}

void test1(){

  int a = 10;
  double b = 10.0;

  /* !!
  int a = 10.0 을 입력하면 자동완성으로
  int a = 10.0 as Int
  가 자동완성되지만, 실행하면 오류남.
  자동완성이 구라를...ㄷㄷ  toInt 해야 함.
  int a = 10.0.toInt();
  double b = 10;

  print("a:${a}");
  print("b:${b}");
  */

  //num 타입에는 int와 double 모두 대입할 수 있음
  num c = a;
  num d = b;

  //var 를 통한 타입 추론 !! 한번 정해지면 타입 수정 불가.
  var h = 15; // int
  var i = true; //bool

  // !! dynaic 예제 테스트
  dynamic h2 = "2";
  print('가나다라'
      '마바사');
  print(h2.runtimeType);
  h2 = 22.0;
  print(h2);

  //상수 : final
  final String name = "나잘함";
  //name="너잘함"; // 에러

}

void test2(){

  // var c = 20.5;
  // //error : 서로가 관계가 없기 때문에 변형 불가
  // int d = c as int ;
  // print(d);

  dynamic e = 30.5;
  // int와 double은 num으로 변환 가능
  // !! e 의 데이터 타입 출력해보기  print(e is double); or print(e is int)
  num n1 = e;
  print(e);

}
// !! flutter 개행없이 한줄에 출력하기.  stdout.write("test");

void main(){
  test();
  test1();
  test2();
}