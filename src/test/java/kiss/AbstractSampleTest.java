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
package kiss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import kiss.application.FooService;
import kiss.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public abstract class AbstractSampleTest {

  @Autowired
  protected FooService fooService;

  public final void setFooService(FooService fooService) {
    this.fooService = fooService;
  }

  @Test
  public final void testFooService() {
    this.fooService.doSomeBusinessStuff2("u1");

//    User user = this.fooService.doSomeBusinessStuff("u1");
//    assertNotNull(user);
//    assertEquals("Pocoyo", user.getName());
//    assertEquals(2, user.getPhones().size());

  }

}
