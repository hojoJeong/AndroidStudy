import 'package:flutter/material.dart';

class TabPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: AppBar(
          title: Text('Tab'),
          bottom: TabBar(
            tabs: <Widget>[
              Tab(icon: Icon(Icons.tag_faces)),
              Tab(text: '공지사항'),
              Tab(icon: Icon(Icons.wifi_rounded), text: '네트워크'),
            ],
          ),
        ),
        body: TabBarView(
            children: <Widget>[
              Container(color: Colors.yellow,),
              Container(color: Colors.orange,),
              Container(color: Colors.black26,),
            ]),
      ),
    );
  }
}
