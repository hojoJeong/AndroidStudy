import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CupertinoPage extends StatefulWidget {
  @override
  _CupertinoPageState createState() => _CupertinoPageState();
}

class _CupertinoPageState extends State<CupertinoPage> {
  var _isOn = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CupertinoNavigationBar(
        middle: Text('9. 쿠퍼티노 디자인')
      ),
      body: Column(
        children: <Widget>[
          CupertinoSwitch(
            value: _isOn,
            onChanged: (bool value) {
              setState(() {
                _isOn = value;
              });
            },
          ),
          CupertinoButton(
            borderRadius: BorderRadius.circular(16.0),
            color: Colors.orange,
            child: Text('쿠퍼티노 AlertDialog'),
            onPressed: () {
              _showCupertinoDialog();
            },
          ),
          CupertinoButton(
            child: Text('쿠퍼티노 Picker'),
            onPressed: () {
              _showCupertinoPicker();
            },
          ),
        ],
      ),
    );
  }

  _showCupertinoDialog() {
    showDialog(
      context: context,
      builder: (context) => CupertinoAlertDialog(
        title: Text('제목'),
        content: Text('내용'),
        actions: <Widget>[
          CupertinoDialogAction(
            child: Text('Cancel'),
          ),
          CupertinoDialogAction(
            child: Text('Ok'),
            onPressed: () {
              Navigator.of(context).pop();
            },
          ),
        ],
      ),
    );
  }


  _showCupertinoPicker() async {
    final _items = List.generate(10, (i) => i);
    var result = _items[0];
    await showCupertinoModalPopup(
      context: context,
      builder: (context) => Container(
        height: 200.0,
        child: CupertinoPicker(
          children: _items.map((e) => Text('No. $e')).toList(),
          itemExtent: 50.0,
          onSelectedItemChanged: (int value) {
            result = _items[value];
          },
        ),
      ),
    );
    print(result);
  }
}
