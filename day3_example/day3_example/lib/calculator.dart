import 'dart:core';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: WidgetApp(),
    );
  }
}

class WidgetApp extends StatefulWidget {
  const WidgetApp({Key? key}) : super(key: key);

  @override
  State<WidgetApp> createState() => _WidgetAppState();
}

class _WidgetAppState extends State<WidgetApp> {
  List _list = ["더하기", "빼기", "곱하기", "나누기"];
  List<DropdownMenuItem<String>> _dropdownItem = [];
  String buttonText = ''; //메뉴 클릭하면 버튼 text 설정
  String result = ''; //결과 출력

  TextEditingController value1 = TextEditingController();
  TextEditingController value2 = TextEditingController();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    for (var item in _list) {
      _dropdownItem.add(DropdownMenuItem(child: Text(item), value: item));
    }
    buttonText = _list[0];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text('계산기 어플')),
        body: Center(
            child: Column(
          children: [
            Text("결과 : $result"),
            Padding(
                padding: const EdgeInsets.all(8.0),
                child: TextField(
                  controller: value1,
                  keyboardType: TextInputType.number,
                )),
            Padding(
                padding: const EdgeInsets.all(8.0),
                child: TextField(
                  controller: value2,
                  keyboardType: TextInputType.number,
                )),
            ElevatedButton(onPressed: () {
              var first = double.parse(value1.value.text);
              var second = double.parse(value2.value.text);
                setState(() {
                  switch(buttonText){
                    case '더하기':{
                      result = (first + second).toString();
                      break;
                    }
                    case '빼기':{
                      result = (first - second).toString();
                      break;
                    }
                    case '곱하기':{
                      result = (first * second).toString();
                      break;
                    }
                    case '나누기':{
                      result = (first / second).toString();
                      break;
                    }
                  }
                });
            }, child: Text(buttonText)),
            DropdownButton(
                items: _dropdownItem,
                onChanged: (value) {
                  setState(() {
                    buttonText = value.toString();
                  });
                },
                value: buttonText,)
          ],
        )
        )
    );
  }
}