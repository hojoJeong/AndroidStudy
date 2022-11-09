import 'package:flutter/material.dart';

class CheckRadioSwitchPage extends StatefulWidget {
  @override
  _CheckRadioSwitchPageState createState() => _CheckRadioSwitchPageState();
}

class _CheckRadioSwitchPageState extends State<CheckRadioSwitchPage> {
  var _isChecked = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Checkbox / Radio / Switch')
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Checkbox(
                value: _isChecked,
                onChanged: (value) {
                  setState(() {
                    _isChecked = value!;
                  });
                },
              ),
              SizedBox(
                height: 40,
              ),
              Switch(
                value: _isChecked,
                onChanged: (value) {
                  setState(() {
                    _isChecked = value;
                  });
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
