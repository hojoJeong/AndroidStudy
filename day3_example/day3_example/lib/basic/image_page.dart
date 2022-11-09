import 'package:flutter/material.dart';

class ImagePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Image')
      ),
      body: Center(
        child: Image.asset('assets/flutter.png')
      ),
    );
  }
}
