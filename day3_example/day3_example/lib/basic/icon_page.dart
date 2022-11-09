import 'package:flutter/material.dart';

class IconPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Icon')
      ),
      body: Center(
        child: Icon(
          Icons.home,
          color: Colors.red,
          size: 60.0, // 기본값 24.0
        ),
      ),
    );
  }
}
