import 'package:flutter/material.dart';
import 'package:day3_example/animation/animation_menu.dart';
import 'package:day3_example/basic/basic_menu.dart';
import 'package:day3_example/button/button_menu.dart';
import 'package:day3_example/cupertino/cupertino_menu.dart';
import 'package:day3_example/dialog/dialog_menu_page.dart';
import 'package:day3_example/event/event_menu_page.dart';
import 'package:day3_example/input/input_menu.dart';
import 'package:day3_example/layout/layout_menu.dart';
import 'package:day3_example/multi/multi_menu_page.dart';
import 'package:day3_example/navigation/navigation_menu_page.dart';
import 'package:day3_example/navigation/stateful_page.dart';
import 'package:day3_example/navigation/stateless_page.dart';
import 'package:day3_example/realworldui/real_world_menu_page.dart';
import 'package:url_launcher/url_launcher.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      routes: {
        '/first': (context) => FirstPage(),
        '/second': (context) => SecondStatefulPage(),
      },
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MenuPage(),
    );
  }
}

class MenuPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('flutter_3days_sample'),
      ),
      body: ListView(
        children: <Widget>[
          ListTile(
            title: Text('1. 화면 배치를 위한 위젯'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => MultiMenu()),
              );
            },
          ),
          ListTile(
            title: Text('2. 위치, 정렬, 크기를 위한 위젯'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => LayoutMenu()),
              );
            },
          ),
          ListTile(
            title: Text('3. 버튼 계열 위젯'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => ButtonMenu()),
              );
            },
          ),
          ListTile(
            title: Text('4. 화면 표시용 위젯'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => BasicMenu()),
              );
            },
          ),
          ListTile(
            title: Text('5. 입력용 위젯'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => InputMenu()),
              );
            },
          ),
          ListTile(
            title: Text('6. 다이얼로그'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => DialogMenuPage()),
              );
            },
          ),
          ListTile(
            title: Text('7. 이벤트'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => EventMenuPage()),
              );
            },
          ),
          ListTile(
            title: Text('8. 애니메이션'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => AnimationMenuPage()),
              );
            },
          ),
          ListTile(
            title: Text('9. 쿠퍼티노 디자인'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => CupertinoPage()),
              );
            },
          ),
          ListTile(
            title: Text('10. 복잡한 UI 작성 <추가 내용>'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => RealWorldMenuPage()),
              );
            },
          ),
          ListTile(
            title: Text('11. 네비게이션'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => NavigationMenuPage()),
              );
            },
          ),
        ],
      ),
    );
  }
}

launchURL(String url) async {
  if (await canLaunch(url)) {
    await launch(url);
  } else {
    throw 'Could not launch $url';
  }
}
