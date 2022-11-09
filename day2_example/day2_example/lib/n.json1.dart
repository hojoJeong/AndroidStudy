import 'dart:convert';

//Json Decoding
  void main(){
    var jsonString = '''
    [
      {"name":"홍길동"},
      {"name":"손홍민"}
    ]
    ''';

    var info = jsonDecode(jsonString);
    print(info);
    //List 인지 체크
    print(info is List);
    //첫번째 Key:Value
    print(info[0]);
    print(info[0]['name']);
  }


//Json Encoding

  // void main(){
  //   //'' -> 변수임을 나타냄
  //   var infoList = [{'menu':"삼겹살"},
  //     {'name':"탕수육"},
  //     {'menu':"보쌈"},
  //     {'menu':"짬뽕",'price':10000}
  //     ];
  //   // !! infoList를 출력해서 둘간의 차이를 보자. ""로 감싼것이 차이
  //   print(infoList);
  //   var infoText = jsonEncode(infoList);
  //   print(infoText);
  //
  // }






