import 'package:flutter/material.dart';

class Person2 {
  String name;
  int age;

  Person2(this.name, this.age);
}

class FirstPage2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print('FirstPage2 build()');
    return Scaffold(
      appBar: AppBar(
        title: Text('First')
      ),
      body: ElevatedButton(
        child: Text('다음 페이지로'),
        onPressed: () async {
          final person = Person2('홍길동', 20);
          final result = await Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => SecondPage2(person: person)),
          );

          print(result);
        },
      ),
    );
  }
}

class SecondPage2 extends StatelessWidget {
  final Person2 person;

  SecondPage2({Key? key, required this.person}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    print('SecondPage2 build()');
    return Scaffold(
      appBar: AppBar(
        title: Text(person.name),
      ),
      body: ElevatedButton(
        child: Text('이전 페이지로'),
        onPressed: () {
          Navigator.pop(context, 'Save OK');
        },
      ),
    );
  }
}

