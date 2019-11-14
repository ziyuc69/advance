package cn.glenn.respontry;

import cn.glenn.dataobject.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: wangxg
 * @date: 2019-07-20 19:30
 **/
public interface UserRepository extends JpaRepository<User, Integer> {
}
