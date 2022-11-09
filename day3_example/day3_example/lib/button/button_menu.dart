import 'package:flutter/material.dart';
import 'package:day3_example/button/fab_button_page.dart';
import 'package:day3_example/button/text_button_page.dart';
import 'package:day3_example/button/icon_button_page.dart';
import 'package:day3_example/button/elevated_button_page.dart';

class ButtonMenu extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('3. 버튼 계열 위젯')
      ),
      body: ListView(
        children: <Widget>[
          ListTile(
            title: Text('ElevatedButton'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => ElevatedButtonPage()),
              );
            },
          ),
          ListTile(
            title: Text('TextButton'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TextButtonPage()),
              );
            },
          ),
          ListTile(
            title: Text('IconButton'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => IconButtonPage()),
              );
            },
          ),
          ListTile(
            title: Text('FloatingActionButton'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => FloatingActionButtonPage()),
              );
            },
          ),
        ],
      ),
    );
  }
}
