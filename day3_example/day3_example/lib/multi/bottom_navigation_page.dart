import 'package:flutter/material.dart';
import 'package:day3_example/main.dart';

class BottomNavigationPage extends StatefulWidget {
  @override
  _BottomNavigationPage createState() => _BottomNavigationPage();
}

class _BottomNavigationPage extends State<BottomNavigationPage> {

  int _selectedIndex = 0;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('BottomNavigationBar')
      ),
      bottomNavigationBar: BottomNavigationBar(
          currentIndex: _selectedIndex, //현재 선택된 Index
          onTap: (int index) {
            setState(() {
              _selectedIndex = index;
            });
          },
          items: [
        BottomNavigationBarItem(
          icon: Icon(Icons.home),
          label: '홈',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.person),
          label: '개인정보',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.notifications),
          label: '알림',
        ),
      ]),
      body: Center(
        child: _widgetOptions.elementAt(_selectedIndex),
      ),
    );
  }
}

List _widgetOptions = [
  Text(
    '홈',
    style: TextStyle(fontSize: 30),
  ),
  Text(
    '개인정보',
    style: TextStyle(fontSize: 30),
  ),
  // FirstPage(),
  Text(
    '알림',
    style: TextStyle(fontSize: 30),
  ),
];
