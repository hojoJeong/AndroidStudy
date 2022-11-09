
class Fly{
  void doSomething(){
    print("Fly");
  }
}
class Person{
  @override
  void doSomething(){
    print("Person");
  }
}

//Teacher는 상속 받은 기능외에 Fly의 기능도
//가지고 있다.

//!! polymorphism 테스트
class Teacher extends Person with Fly{
  //do Something ~
  @override
  void doSomething(){
    print("Teacher");
  }
}

void main(){
  Fly f = new Teacher();
  f.doSomething();
  Person p = new Teacher();
  p.doSomething();
  Teacher t = new Teacher();
  t.doSomething();
}
