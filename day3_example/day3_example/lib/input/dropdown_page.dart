import 'package:flutter/material.dart';

class DropdownPage extends StatefulWidget {
  @override
  _DropdownPageState createState() => _DropdownPageState();
}

class _DropdownPageState extends State<DropdownPage> {
  final _valueList = ['Java', 'Android', 'Kotlin'];
  var _selectedValue = 'Java';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Dropdown Button'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Center(
          child: DropdownButton(
            value: _selectedValue,
            items: _valueList.map(
              (value) {
                return DropdownMenuItem(
                  value: value,
                  child: Text(value),
                );
              },
            ).toList(),
            onChanged: (value) {
              setState(() {
                _selectedValue = value.toString();
              });
            },
          ),
        ),
      ),
    );
  }
}
