
//2. 비동기 함수가 반환하는 값 이용하기 ===================================

void main() async{
  print('start main');
  await checkData().then((value) => {print(value)});
  print('end main');
}

Future checkData() async {
  var data = await getAllData();
  return data;
}

String getAllData(){
  return 'Hello Dart';
}

//3. delayed와 await 이용한 흐름 제어 ===================================

// void main(){
//   print('start main');
//   getData1();
//   getData2();
//   getData3();
//   print('end main');
// }
// void getData1(){
//   print('getData1 called..');
// }
// void getData2() async{
//   await Future.delayed(Duration(seconds: 2), () =>
//     print('Future method called...')
//   );
//   print('getData2 called..');
// }
// void getData3(){
//   print('getData3 called..');
// }

