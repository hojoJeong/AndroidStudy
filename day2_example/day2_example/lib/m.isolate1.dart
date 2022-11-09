import 'dart:isolate';

  void main(){
    Isolate.spawn(sendMessage, '1. Kotlin');
    Isolate.spawn(sendMessage, '2. Android');
    Isolate.spawn(sendMessage, '3. Hybrid');

    // !! dalay 시켜서 main thread 내의 spawn 모두 실행시키기.
    Future.delayed(const Duration(seconds: 2), () =>
      print('Future method called...')
    );
  }

  void sendMessage(var message) {
    print('This is a ${message}');
  }

