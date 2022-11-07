import 'package:flutter/material.dart';
import 'package:hello_flutter/main.dart';

void main() {
  runApp(const MyApp2());
}

class MyApp2 extends StatefulWidget {
  const MyApp2({Key? key}) : super(key: key);

  @override
  State<MyApp2> createState() => _MyApp2State();
}

class _MyApp2State extends State<MyApp2> {
  String _test = 'hello';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Simple Flutter App',
      theme: ThemeData(primarySwatch: Colors.lightBlue),
      home: Scaffold(
        appBar: AppBar(title: Text('simple flutter app')),
        body: Center(
          child: ElevatedButton(
            onPressed: () {
              print('isClicked...');
              if (_test == 'hello') {
                setState((){
                  _test = 'flutter';
                });
              } else {
                setState((){
                  _test = 'hello';
                });
              }
            },
            child: Text('$_test입니다~~'),
          ),
        ),
      ),
    );
  }
}
