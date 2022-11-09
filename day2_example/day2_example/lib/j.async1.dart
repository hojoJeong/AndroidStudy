


  //1. async와 await을 이용한 비동기 코드 구현 ===========================

  void main(){
    print('start main');
    var future = checkData();
    print('end main');
    print(future);
  }
  Future checkData() async {
    var data = await getAllData();
    print(data);
  }
  int getAllData(){
    return 1000;
  }

