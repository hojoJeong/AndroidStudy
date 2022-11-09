import 'package:flutter/material.dart';

class TextButtonPage extends StatelessWidget {

  var count = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(

      appBar: AppBar(
        title: Text('TextButtonPage')
      ),
      body: Center(
        child: TextButton(
          child: Text('TextButton Click'),
          onPressed: () {
            count++;
            print("증가된 값은 $count");
          },
        ),
      ),
    );
  }
}
