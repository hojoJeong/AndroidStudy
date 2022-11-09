import 'package:flutter/material.dart';
import 'package:day3_example/dialog/alert_dialog_page.dart';
import 'package:day3_example/dialog/date_picker_page.dart';
import 'package:day3_example/dialog/time_picker_page.dart';

class DialogMenuPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('6. 다이얼로그'),
      ),
      body: ListView(
        children: <Widget>[
          ListTile(
            title: Text('AlertDialog'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => AlertDialogPage()),
              );
            },
          ),
          ListTile(
            title: Text('DatePicker'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => DatePickerPage()),
              );
            },
          ),
          ListTile(
            title: Text('TimePicker'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TimePickerPage()),
              );
            },
          ),
        ],
      ),
    );
  }
}
