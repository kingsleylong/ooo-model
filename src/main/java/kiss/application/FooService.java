/**
 *    Copyright 2010-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package kiss.application;

import kiss.domain.user.Phone;
import kiss.domain.user.User;
import kiss.domain.user.UserRepository;
import kiss.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * FooService simply receives a userId and uses a mapper/dao to get a record from the database.
 */
@Transactional
public class FooService {

  private UserMapper userMapper;

  @Autowired
  private UserRepository userRepository;

  public void setUserMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public User doSomeBusinessStuff(String userId) {
    return this.userMapper.getDetailedUser(userId);
  }

  public void doSomeBusinessStuff2(String userId) {
    userRepository.editingMode();
    User detailedUser = userRepository.find(userId);
//    User detailedUser = this.userMapper.getDetailedUser(userId);
    detailedUser.getPhones().clear();
    detailedUser.getPhones().add(new Phone("p5","125122352", ""));
    System.out.println(detailedUser.getPhones().toArray());
    userRepository.store(detailedUser);
  }

  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
