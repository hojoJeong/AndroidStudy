import 'package:flutter/material.dart';

class SizedBoxPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('SizedBox')
      ),
      body: SizedBox(
        width: 100,
        height: 100,
        child: Container(
          color: Colors.lightGreenAccent,
        ),
      ),
    );
  }
}
