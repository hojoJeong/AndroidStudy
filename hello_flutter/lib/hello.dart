import 'package:flutter/material.dart';

void main() => runApp(MyApplication());

class MyApplication extends StatelessWidget {
  const MyApplication({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MyStatefull(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class MyStatefull extends StatefulWidget {
  const MyStatefull({Key? key}) : super(key: key);

  @override
  State<MyStatefull> createState() => _MyStatefullState();
}

class _MyStatefullState extends State<MyStatefull> {
  @override
  void initState() {
    //처음에 만들어짐
    super.initState();
    print('initState...');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.amber,
          title: Text("MyStatefull"),
        ),
        body: Column(children: [
          Expanded(
            flex: 1,
            child: Container(
              color: Colors.pink,
              width: 300,
              //비율에 따른 픽셀로 계산 됨
              height: 100,
              margin: EdgeInsets.all(8.0),
              padding: EdgeInsets.all(5.0),
              alignment: Alignment.bottomCenter,
              child: Text('hello'),
            ),
          ),
          Expanded(
              flex: 1,
              child: SizedBox(
                  height: 0)
          ),
          Expanded(
            flex: 2,
            child: Container(
              color: Colors.pink,
              width: 300,
              //비율에 따른 픽셀로 계산 됨
              height: 100,
              margin: EdgeInsets.all(8.0),
              padding: EdgeInsets.all(5.0),
              alignment: Alignment.bottomCenter,
              child: Text('hello'),
            ),
          ),
          Expanded(
            flex: 3,
            child: Container(
              color: Colors.pink,
              width: 300,
              //비율에 따른 픽셀로 계산 됨
              height: 100,
              margin: EdgeInsets.all(8.0),
              padding: EdgeInsets.all(5.0),
              alignment: Alignment.bottomCenter,
              child: Text('hello'),
            ),
          )
        ]
        )
    );
  }
}
