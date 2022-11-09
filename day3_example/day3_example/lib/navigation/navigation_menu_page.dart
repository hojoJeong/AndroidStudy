import 'package:flutter/material.dart';
import 'package:day3_example/navigation/stateful_page.dart';
import 'package:day3_example/navigation/stateless_page.dart';
import 'package:day3_example/navigation/stateless_page2.dart';

class NavigationMenuPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('11. 네비게이션')
      ),
      body: ListView(
        children: <Widget>[
          ListTile(
            title: Text('StatelessWidget의 네비게이션'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => FirstPage()),
              );
            },
          ),
          ListTile(
            title: Text('StatelessWidget2의 네비게이션'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => FirstPage2()),
              );
            },
          ),
          ListTile(
            title: Text('StatefulWidget의 네비게이션'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => FirstStatefulPage()),
              );
            },
          ),
          ListTile(
            title: Text('routes: FirstPage'),
            onTap: () {
              Navigator.pushNamed(context, '/first');
            },
          ),
          ListTile(
            title: Text('routes: SecondPage'),
            onTap: () {
              Navigator.pushNamed(context, '/second');
            },
          ),
        ],
      ),
    );
  }
}
