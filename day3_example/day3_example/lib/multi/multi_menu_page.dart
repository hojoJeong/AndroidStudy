import 'package:flutter/material.dart';
import 'package:day3_example/multi/bottom_navigation_page.dart';
import 'package:day3_example/multi/column_page.dart';
import 'package:day3_example/multi/container_page.dart';
import 'package:day3_example/multi/gridview_page.dart';
import 'package:day3_example/multi/listview_page.dart';
import 'package:day3_example/multi/pageview_page.dart';
import 'package:day3_example/multi/row_page.dart';
import 'package:day3_example/multi/single_child_scroll_page.dart';
import 'package:day3_example/multi/stack_page.dart';
import 'package:day3_example/multi/tab_page.dart';

class MultiMenu extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('1. 화면 배치를 위한 위젯'),
      ),
      body: ListView(
        children: <Widget>[
          ListTile(
            title: Text('Container'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => ContainerPage()),
              );
            },
          ),
          ListTile(
            title: Text('Column'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => ColumnPage()),
              );
            },
          ),
          ListTile(
            title: Text('Row'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => RowPage()),
              );
            },
          ),
          ListTile(
            title: Text('Stack'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => StackPage()),
              );
            },
          ),
          ListTile(
            title: Text('SingleChildScrollView'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => SingleChildScrollViewPage()),
              );
            },
          ),
          ListTile(
            title: Text('ListView / ListTile'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => ListViewPage()),
              );
            },
          ),
          ListTile(
            title: Text('GridView'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => GridViewPage()),
              );
            },
          ),
          ListTile(
            title: Text('PageView'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => PageViewPage()),
              );
            },
          ),
          ListTile(
            title: Text('AppBar / TabBar / Tab / TabBarView'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TabPage()),
              );
            },
          ),
          ListTile(
            title: Text('BottomNavigationBar'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => BottomNavigationPage()),
              );
            },
          ),
        ],
      ),
    );
  }
}
