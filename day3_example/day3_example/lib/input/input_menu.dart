import 'package:flutter/material.dart';
import 'package:day3_example/basic/circle_avatar_page.dart';
import 'package:day3_example/basic/progress_page.dart';
import 'package:day3_example/input/check_switch_page.dart';
import 'package:day3_example/input/dropdown_page.dart';
import 'package:day3_example/input/radio_page.dart';
import 'package:day3_example/input/textfield_page.dart';
import 'package:day3_example/input/textfield_page2.dart';

class InputMenu extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('5. 입력용 위젯'),
      ),
      body: ListView(
        children: <Widget>[
          ListTile(
            title: Text('TextField'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TextFieldPage()),
              );
            },
          ),
          ListTile(
            title: Text('TextField2_stateful'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TextFieldPage2()),
              );
            },
          ),
          ListTile(
            title: Text('CheckBox / Switch'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => CheckRadioSwitchPage()),
              );
            },
          ),
          ListTile(
            title: Text('Radio / RadioListTile'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => RadioPage()),
              );
            },
          ),
          ListTile(
            title: Text('DropDownButton'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => DropdownPage()),
              );
            },
          ),
        ],
      ),
    );
  }
}
