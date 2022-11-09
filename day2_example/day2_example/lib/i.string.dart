
// https://dart.dev/guides/language/effective-dart/usage#strings
void main() {
  print('1하나One\u{1D11E}}');
  print('1하나One\u{1D11E}}'.length); //9 /int /unicode문자를 두 개로 셈
  print('1하나One\u{1D11E}}'.runes.length); //8 /int /unicode 문자를 한 개로 셈
  print('1하나One\u{1D11E}}'.codeUnits); //[49, 54616, 45208, 79, 110, 101, 55348, 56606, 125] /List<int> /각 문자를 UTF-16 code로 바꿔 list에 담음
  print('1하나One\u{1D11E}}'.codeUnitAt(2)); //45208 /int /인덱스 0부터
  print('1하나One\u{1D11E}}'.hashCode); //248270589 /int /해시코드 변환
  print('1'.isEmpty); //false
  print(''.isEmpty); //true
  print('1'.isNotEmpty); //true
  print(''.isNotEmpty); //false
  bool b = false;
  print(b.runtimeType);//bool /Type /객체 타입 알려줌
  int a = 123;
  print(a.toString()); //123 /String /스트링타입으로 변환
  print('1234567890'.substring(3)); //4567890 /String /시작인덱스 지정. 0부터 시작
  print('1234567890'.substring(3,7)); //4567 /시작, 끝 인덱스 지정. 0부터 시작 String 끝 인덱스 앞까지

  //compareTo /int /문자열 정렬 순서 비교. 같으면 0, 더 앞이면 -1, 뒤면 1.
  print('asdf'.compareTo('asdf'));//0
  print('asdf'.compareTo('asdd'));//1
  print('asdf'.compareTo('asdg'));//-1
  print('aaaa'.compareTo('aaa'));//1
  print('aaaa'.compareTo('aaaaa'));//-1
  print('aaaa'.compareTo('aaaA'));//1
  print('asdf1234'.contains('as'));//true /bool /문자열에 포함하고 있는지 체크
  print('asdf1234'.contains('as',1));//false /bool /시작인덱스 지정 가능
  print('qwerasdf'.endsWith('asdf'));//true /bool /문자열 끝 비교
  print('asdfasdf'.indexOf('sd'));//1 /int /앞부터 검사하여 처음 일치하는 index 알려줌
  print('asdfasdf'.indexOf('123'));//-1 /int /없으면 -1
  print('asdfasdf'.indexOf('sd',2));//5 /int /시작인덱스 지정 가능
  print('asdfasdf'.lastIndexOf('sd'));//5 /int /뒤부터 검사하여 처음 일치하는 index 알려줌
  print('asdfasdf'.lastIndexOf('12'));//-1 /int /없으면 -1
  print('asdfasdf'.lastIndexOf('sd',4));//1 /시작인덱스 지정 가능. 시작인덱스부터 앞으로 가며 검사
  print('S'+'asdf'.padLeft(6));//S  asdf /String /문자열의 길이가 지정된 숫자보다 짧으면 왼쪽에 공백을 추가해 길이를 맞춤. 넘어가는 경우는 추가하지 않음
  print('S'+'asdf'.padLeft(6,'*'));//S**asdf /채우는 문자 변경 가능
  print('asdf'.padRight(7) + 'E');//asdf   E /String /문자열의 길이가 지정된 숫자보다 짧으면 왼쪽에 공백을 추가해 길이를 맞춤. 넘어가는 경우는 추가하지 않음
  print('asdf'.padRight(7,'+') + 'E');//asdf+++E /채우는 문자 변경 가능
  print('asdfasdf'.replaceAll('as','12'));//12df12df /String /일치하는 모든 문자를 교체함
  int c = 10;
  print('asdfasdf'.replaceAllMapped('as',(match){
    c++;
    return c.toString();
  }));//11df12df /String /일치하는 모든 문자를 앞부터 교체하는데.. 함수 사용
  print('asdfasdf'.replaceFirst('as','12'));//12dfasdf /String /첫번째로 일치하는 문자를 교체함
  double d = 20;
  print('asdfasdf'.replaceFirstMapped('as',(match){
    d = d/4;
    return d.toString();
  }));//5dfasdf /String /첫번째로 일치하는 문자를 교체하는데.. 함수 사용
  print('asdfasdf'.replaceRange(2,5,'1234'));//as1234sdf /String /시작인덱스~끝인덱스 교체
  print('asdf**1234'.split('*'));//[asdf,,1234] /List<String> /나누는 문자를 기준으로 나눠서 List로 만듬
  print('asdf'.split(''));//[a,s,d,f] /이렇게하면 문자마다 나눔
  print('asdf**12*34'.splitMapJoin(
      '*',
      onMatch: (Match m){
        return '${m[0]!}' + '+';
      },
      onNonMatch: (String n){
        return n+'A';
      })); //asdfA*+A*+12A*+34A /String /일치하는 부분 함수이용해서 교체, 불일치 부분(일치로 인해 분리된 부분들) 함수 이용해서 교체
  print('asdfqwer'.startsWith('as'));//true /bool /시작 문자를 검사
  print('asdfqwer'.startsWith('qw',4));//true /bool /시작 인덱스 가능
  print('AsDf'.toLowerCase());//asdf
  print('AsDf'.toUpperCase());//ASDF
  print('S'+'   asdf   '.trim()+'E');//SasdfE /String /문자열 앞 뒤 공백을 제거 \n \t 등
  print('S'+'   asdf   '.trimLeft()+'E');//Sasdf   E /String /문자열 앞의 공백 제거
  print('S'+'   asdf   '.trimRight()+'E');//S   asdfE /String /문자열 뒤의 공백을 제거
}