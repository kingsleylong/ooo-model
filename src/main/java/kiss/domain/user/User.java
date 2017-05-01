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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.ibatis.annotations.Mapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple bean that holds User info.
 */
@Entity
@ToString(exclude = "account")
@Data
@AllArgsConstructor
public class User implements Serializable{

  @Id
  private String id;

  private String name;

  @OneToMany(targetEntity = Phone.class, cascade = CascadeType.ALL, mappedBy = "user")
  private Set<Phone> phones;

  @Transient
  private String account;

  public User deepClone() {
    Set<Phone> phonesClone = new HashSet<Phone>(phones.size());
    User userClone = new User(id, name, phonesClone, null);
    for (Phone phone : phones) {
      phonesClone.add(new Phone(phone.getId(), phone.getNumber(), phone.getBand(), userClone));
    }
    return userClone;
  }

  public boolean equalTo(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    return new EqualsBuilder()
            .append(id, user.id)
            .append(name, user.name)
            .isEquals();
  }
}
