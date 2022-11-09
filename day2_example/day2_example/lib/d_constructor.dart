
class Product{
  String? serNo;
  String? name;
  //선택 매개변수 사용함
  // !! 선택 매개변수 테스트 진행.
  Product({String? serNo, String? name}){
    this.serNo = serNo;
    this.name = name;
  }
}
void main(){
  var pro1 = Product();
  var pro2 = Product(serNo: "1234", name: "TV");
}

