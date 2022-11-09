import 'package:flutter/material.dart';

class GesturePage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('GestureDetector')
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            GestureDetector(
              onTap: () {
                print('GestureDetector 클릭!!');
              },
              child: Text('클릭 Me!!'),
            ),
            SizedBox(
              height: 40,
            ),
            InkWell(
              onTap: () {
                print('InkWell 클릭!!');
              },
              child: Text('클릭 Me!!'),
            ),
          ],
        ),
      ),
    );
  }
}
