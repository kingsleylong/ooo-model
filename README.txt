ObjectPersist by MyBatis

这是一个基于MyBatis的轻量级对象持久化框架，实现了Hibernate的部分级联保存功能。主要是为了在不能使用Hibernate的情况下，为领域驱动开发提供一套面向集合的持久化框架。

使用注意 ：
1. 实体必须用JPA注解标识。如：实体类需增加Entity注解；id属性增加Id注解；一对多的一方要增加OneToMany注解，关联集合，多方则须增加一个一方的对象引用，增加ManyToOne注解。注意：如果采用Lombok自动生成equals和hashCode方法，必须在多方去掉一方的引用，否则会产生循环引用，导致栈溢出。
2. 所有实体必须提供一个equalTo方法，此方法与equals的唯一区别是：关联的集合不参与计算。目的的为了对比实体对象本身属性的变化，而非关联对象。无关联对象的情况下可直接调用equals方法，参考Phone实体。
3. deepClone
