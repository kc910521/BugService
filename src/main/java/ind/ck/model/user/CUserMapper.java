package ind.ck.model.user;

import java.util.List;

import ind.ck.model.bean.CommonUser;

import org.apache.ibatis.annotations.*;

public interface CUserMapper {
    @Select("select * from test.t_user user where user.id=#{id}")
    @Results(
      {
        @Result(id = true, column = "id", property = "id"),
        @Result(column = "username", property = "username"),
        @Result(column = "password", property = "password"),
        @Result(column = "age", property = "age"),
        @Result(column = "ibirthday", property = "ibirthday")
      })
    CommonUser findById(int id);
    
    @Insert("INSERT INTO test.t_user (username, password,ibirthday) " +
      "VALUES (#{username}, #{password},#{ibirthday} )")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertUser(CommonUser user);
    
    
    @Insert ("INSERT INTO  t_user (username,password,ibirthday) "
    		+ " VALUS (#{username}, #{password},#{ibirthday}) "
    		+ " WHERE #{condition} =  #{condVal}")
    void insertUserBy(CommonUser user,String condition,String condVal);
    
    
/*    @Insert("INSERT INTO blog_db.user (name, email, password) " +
      "VALUES ( #{user.name}, #{user.email}, CONCAT(#{user.password}, #{passwordSuffix}) )")
    void insertUser2(@Param("user")CommonUser user, @Param("passwordSuffix")String passwordSuffix);*/
    
    @Select (" SELECT * FROM t_user limit 0,#{maxResult} ")
    @Results ({
    		@Result (id = true,column = "id",property = "id"),
    		@Result (column = "username",property="username"),
    		@Result (column = "age",property = "age")
    		})
    public List<CommonUser> findAllUser(int maxResult);
    
    
}
