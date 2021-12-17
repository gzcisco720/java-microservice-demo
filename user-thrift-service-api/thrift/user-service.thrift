namespace java com.demo.thrift.user.service.api

struct UserInfo {
  1:i32 id,
  2:string username,
  3:string password,
  4:string nickname,
  5:string mobile,
  6:string email,
  7:string intro,
  8:i32 rate
}

service UserService {
  UserInfo getUserById(1:i32 id);
  UserInfo getTeacherById(1:i32 id);
  UserInfo getUserByName(1:string username);
  void registerUser(1:UserInfo userinfo);
}
