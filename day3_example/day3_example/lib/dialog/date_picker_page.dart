import 'package:flutter/material.dart';

class DatePickerPage extends StatefulWidget {
  @override
  _DatePickerPageState createState() => _DatePickerPageState();
}

class _DatePickerPageState extends State<DatePickerPage> {
  DateTime _selectedTime = DateTime.now();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('DatePicker')
      ),
      body: Column(
        children: <Widget>[
          ElevatedButton(
            onPressed: () {
              Future<DateTime?> selectedDate = showDatePicker(
                context: context,
                initialDate: DateTime.now(), // 초깃값
                firstDate: DateTime(2018), // 시작일
                lastDate: DateTime(2030), // 마지막일
                builder: (BuildContext context, Widget? child) {
                  return Theme(
                    data: ThemeData.fallback(),
                    // data: ThemeData.dark(), // 다크테마
                    child: child!,
                  );
                },
              );

              selectedDate.then((dateTime) {
                setState(() {
                  _selectedTime = dateTime!;
                });
              });
            },
            child: Text('Date Picker'),
          ),
          Text('$_selectedTime'),
        ],
      ),
    );
  }
}
