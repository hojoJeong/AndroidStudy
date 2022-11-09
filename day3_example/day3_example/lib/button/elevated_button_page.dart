import 'package:flutter/material.dart';

class ElevatedButtonPage extends StatelessWidget {
  var count = 0;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('ElevatedButtonPage')
      ),
      body: Center(
        child:
        ElevatedButton(
          child: Text('ElevatedButton'),
          onPressed: () {
            count++;
            print("증가된 값은 $count");
          },
        ),
      ),
    );
  }
}
