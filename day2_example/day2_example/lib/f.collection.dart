

void main(){

  // List =====================================
  var player = ['손홍민', '메시', '호날두'];

  print('넘버원 player : ${player[0]}');

  for(int i=0;i<player.length;i++){
    print(player[i]);
  }


  //Map ======================================

  //Map 생성
  // var result = {
  //   '최고야':90,
  //   '너정말':55,
  //   '심하다':30
  // };
  // //값 조회
  // print(result['최고야']);
  // //값 수정
  // result['최고야'] = 98;
  // //수정 값 조회
  // print(result);

  //Set ======================================
  // var prod = {'TV','에어콘','내장고','TV'};
  // //중복된 데이터를 허용하지 않음
  // print(prod); // 결과값 : {TV, 에어콘, 내장고}
  // prod.add('청소기');
  // print(prod.contains('TV'));

}

