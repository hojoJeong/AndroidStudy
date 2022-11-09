void iftest() {
  int num1 = 3;

  if (num1 % 2 == 0) {
    print('짝수');
  } else {
    print('홀수');
  }
}

void forTest() {
  var model = ['TV', '냉장고', '에어콘'];
  // !! java, kotlin은 set(index, object)로 삽입. 여긴 insert
  model.add("test");
  model.insert(0, "x");

  for (int i = 0; i < model.length; i++) {
    print(model[i]);
  }
  // !! 탐색. for
  for (String j in model) {
    print(j);
  }

  //foreach
  model.forEach((element) {
    print(element);
  });
}

void annoFunTest() {
  var result = (num3) {
    return num3 % 2 == 0;
  }(10);

  print(result);

  // !! 변수에 함수 assign
  var test = (int i) {
    return i * i;
  };

  print(test(10));

  // !! 람다 함수. fat arrow
  var test2 = (int i) => i * i;
  print(test2(20));
}

// !! 선택 매개변수(Named parameter)로 변경해보기.
// void showInfo({String name = "", int age = 0}){
void showInfo(String name, int age) {
  print('name : ${name}, age : ${age}');
}

void showInfo1(String name, {int? age}) {
  print('name : ${name}, age : ${age}');
}

void main2() {
  showInfo1('김열심'); // 정상 실행
  //showInfo1(age: 10); // error
}

void main() {
  // iftest();
  // forTest();
  // annoFunTest();
  //
  // showInfo('김길동', 28);
  // showInfo(name: '김길동', age: 28);
  // showInfo(name : '나간다');
  // showInfo(age:35);
  // showInfo();

  main2();
}
