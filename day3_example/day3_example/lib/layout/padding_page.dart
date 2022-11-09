import 'package:flutter/material.dart';

class PaddingPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Padding')
      ),
      body: Padding(
        padding: const EdgeInsets.all(70.0),
        child: Container(
          color: Colors.orange,
        ),
      ),
    );
  }
}
