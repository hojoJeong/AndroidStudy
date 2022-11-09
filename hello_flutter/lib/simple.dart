import 'package:flutter/material.dart';
import 'package:hello_flutter/main.dart';
void main() {
  runApp(const MyApp2());
}

// void main() => runApp(
//   const MaterialApp(
//     title: 'Simple Flutter App',
//     home: MyApp2()
//   )
// );

class MyApp2 extends StatelessWidget {
  const MyApp2({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: MyHomePage2(),
    );
  }
}
class MyHomePage2 extends StatefulWidget {
  const MyHomePage2({Key? key}) : super(key: key);

  @override
  State<MyHomePage2> createState() => _MyHomePage2State();
}

class _MyHomePage2State extends State<MyHomePage2> {
  String _test = 'Hello';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Simple Flutter App',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: Scaffold(
        appBar: AppBar(title: Text('Simple Flutter App')),
        body: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                ElevatedButton(
                    onPressed: () {
                      // 화면이동. main.dart > MyHomePage
                      Navigator.push(context,
                          MaterialPageRoute(builder: (context) =>
                              MyHomePage(title: 'Flutter Demo Home Page')
                          )
                      );
                    },
                    child: Text(_test,
                      style: TextStyle(fontSize: 24),
                    )
                ),
                ElevatedButton(
                    onPressed: () {
                      print('btn clicked');
                      setState(() {
                        if (_test == 'Hello') {
                          _test = 'flutter';
                        } else {
                          _test = 'Hello';
                        }
                      });
                    },
                    child: Text(_test)
                ),
                ElevatedButton(
                    onPressed: () {
                      print('btn clicked');
                      setState(() {
                        if (_test == 'Hello') {
                          _test = 'flutter';
                        } else {
                          _test = 'Hello';
                        }
                      });
                    },
                    child: Text(_test)
                ),
                ElevatedButton(
                    onPressed: () {
                      print('btn clicked');
                      setState(() {
                        if (_test == 'Hello') {
                          _test = 'flutter';
                        } else {
                          _test = 'Hello';
                        }
                      });
                    },
                    child: Text(_test)
                ),
              ],
            )
        ),
      ),
    );
  }
}