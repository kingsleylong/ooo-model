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
package kiss.infrastructure.mapper;

import kiss.domain.user.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * This DAO extends SqlSessionDaoSupport and uses a Spring managed SqlSession
 * instead of the MyBatis one. SqlSessions are handled by Spring so you don't
 * need to open/close/commit/rollback.
 * MyBatis exceptions are translated to Spring Data Exceptions.
 */
public class UserDaoImpl extends SqlSessionDaoSupport implements UserMapper
{

  @Override
  public User getUser(String userId) {
    return (User) getSqlSession().selectOne("kiss.infrastructure.mapper.UserMapper.getUser", userId);
  }

  @Override
  public User getDetailedUser(String userId) {
    return null;
  }

}