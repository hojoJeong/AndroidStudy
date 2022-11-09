import 'package:flutter/material.dart';

class HeroPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Hero'),
      ),
      body: Center(
        child: GestureDetector(
          onTap: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => HeroDetailPage()),
            );
          },
          child: Hero(
            tag: 'image',
            child: Image.asset(
              'assets/sample.jpg',
              width: 100,
              height: 100,
            ),
          ),
        ),
      ),
    );
  }
}

class HeroDetailPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Hero Detail'),
      ),
      body: GestureDetector(
        //!! 사진 클릭시 현재 창 닫기 적용.
        onTap: (){
          Navigator.of(context).pop();
        },
        child : Hero(
          tag: 'image',
          child: Image.asset('assets/sample.jpg'),
        ),
      ),
    );
  }
}
