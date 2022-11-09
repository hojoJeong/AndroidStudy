import 'dart:math';

void main() {
  forEach();
  // where();
  // map();
  // any();
  // reduce();
}

//forEach =========================
void forEach(){
  var data = ['java','web','spring','android'];
  data.forEach(print);

  //익명함수 표현
  data.forEach((e) {
    print(e);
  });

  // !! toList()는 새로운 컬렉션을 만든다.
  var newList = data.toList();
  print(newList);

  data[0] = "algorithm";
  print(newList);

  //람다 표현식
  data.forEach((e) => print(e));
}

//where 함수 =======================
void where() {
  var numList = [3, 4, 5, 6];
  // 결과 : 4, 6 출력됨
  numList.where((e) {
    print(e);
    return e % 2 == 0;
  }).forEach(print);

}

//map 함수
void map() {
  //map 사용 안한 코드
  var numList1 = [3, 4, 5, 6];
  for (int i = 0; i < numList1.length; i++) {
    if (numList1[i] % 2 == 0) {
      print('결과 : ${numList1[i]}');
    }
  }

  //map 사용 =============================
  numList1.where((e) => e % 2 == 0).map((e) => '결과 : $e').forEach((print));

}


//any 함수 실습  ========================
void any() {
  var numList2 = [4, 5, 6, 8, 10];

  //3의 배수가 있는지 확인
  var result = numList2.any((e) => e % 3 == 0);
  print(result); //true 출력
}

//reduce 함수 실습 =======================
void reduce(){
  //가장 큰 값을 찾는 코드
  //위에서 모듈 임포트함 -> import 'dart:math';

  var numList3 = [ 3, 1, 5, 2, 6, 4 ];
  var myMax = numList3[0];

  for(int i = 1;i<numList3.length;i++){
    myMax = max(numList3[i], myMax);
  }
  print(myMax);

  //reduce 이용 코드
  var myMax1 = numList3.reduce((v, e) => max(v, e));
  print(myMax1);

}

