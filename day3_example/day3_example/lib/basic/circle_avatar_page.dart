import 'package:flutter/material.dart';

class CircleAvatarPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('CircleAvatar')
      ),
      body: Center(
        child: CircleAvatar(
          child: Icon(Icons.person),
        ),
      ),
    );
  }
}
