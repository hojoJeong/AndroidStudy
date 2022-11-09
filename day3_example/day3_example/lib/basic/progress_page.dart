import 'package:flutter/material.dart';

class ProgressPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Progress')
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            CircularProgressIndicator(
              backgroundColor: Colors.orange,
            ),
            SizedBox(
              height: 20,
            ),
            LinearProgressIndicator(
              color: Colors.purpleAccent,
            ),
          ],
        ),
      ),
    );
  }
}
