

class Student{
  String? id;
  String? name;
  void study(String subject){
    print('$subject를 공부합니다.');
  }
}

class Teacher{
  String? name;
  String? _subject;

  String? get subject => _subject;
  set subject(sub) {
      this._subject = sub;
  }
}

void main(){
  var stu = Student();
  stu.id = '20200101';
  stu.name = '홍길동';
  stu.study("Android");

  var tea = Teacher();
  tea.name = '홍길동';
  tea._subject = '하이브리드';

  print(tea._subject);

}


