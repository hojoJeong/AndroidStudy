import 'package:flutter/material.dart';

class ScrollAndBottomButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('상단 스크롤, 하단 버튼')
      ),
      body: Column(
        children: <Widget>[
          Expanded(
            child: ListView(
              children: List.generate(100, (i) => i)
                  .map((i) => ListTile(title: Text('$i')))
                  .toList(),
            ),
          ),
          SizedBox(
            width: double.infinity,
            child: Container(
              height: 50,
              color: Colors.orange,
              child: Center(
                child: InkWell(
                  child: Text("Button"),
                  onTap: () {},
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}
