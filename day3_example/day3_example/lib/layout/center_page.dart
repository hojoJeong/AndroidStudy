import 'package:flutter/material.dart';

class CenterPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Center')
      ),
      body: Center(
        child: Container(
          color: Colors.cyanAccent,
          width: 200,
          height: 200,
        ),
      ),
    );
  }
}
