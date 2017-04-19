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
package kiss.domain.user;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple bean that holds User info.
 */
public class User {

  private String id;

  private String name;

  private Set<Phone> phones;

  public User deepClone() {
    User userClone = new User(id);
    userClone.name = name;
    Set<Phone> phonesClone = new HashSet<Phone>(phones.size());
    userClone.setPhones(phonesClone);
    for (Phone phone : phones) {
      phonesClone.add(new Phone(phone.getId(), phone.getNumber(), phone.getBand()));
    }
    return userClone;
  }

  public Set<Phone> getPhones() {
    return phones;
  }

  public void setPhones(Set<Phone> phones) {
    this.phones = phones;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(30);
    buf.append("{");
    buf.append(id);
    buf.append(", ");
    buf.append(name);
    buf.append("}");
    return buf.toString();
  }

  public User(String id) {
    this.id = id;
  }
}