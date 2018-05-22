# EasySQL
使用起来很简单的sql工具


初始化EasySQL
----------

```
EasySQL.init(context);
```

数据库操作
---

 - 创建数据库

```
EasySQL.with().createDB(String dbname)不用加.db
```

 - 删除数据库

```
EasySQL.with().deleteDatabase(dbname);
```

 - 获取数据库列表

```
EasySQL.with().listName();
```

表操作
---

 - 创建表

```
EasySQL.with().createTable(class<? extend EasyTable>) 默认带ID
EasySQL.with().createTable(class<? extend EasyTable>, boolean hasID) 是否携带ID
```

 - 删除表

```
EasySQL.with().use(String dbname).deleteTable(class<? extend EasyTable>) 删除某数据库中某表
```

增删改查
----

 - 增

```
EasyEntity entity = new EasyEntity();
Table1 table1 = new Table1(); // 各种set
table1EasyEntity.add(table1);
EasySQL.with().use(trim).save(entity);
```

 - 删

```
EasySQL.with().use(trim).delete(Table1.class, "_short = ?", "2");// 后面的两个参数跟Android自带的参数用法基本一样
```

 - 改

```
EasySQL.with().use(String dbname).update(new class<? extend EasyTable>, int id) 更新某表的某条记录
```

 - 查

```
// 查询表（Table1）中的所有数据
ArrayList<Table1> retrieve1 = EasySQL.with().use(trim).retrieve(Table1.class);
```



以下内容为YY内容
```
Arrlist<Table> = EasySQL.with().use(String name).listTable() 获取某数据库所有表
table.getDB 通过表实体得到属于哪个数据库
Arrlist<DB> = table.getDB 也许在多个数据库中存在相同的表
```


不过该库还没有写好呢，鬼知道多久才能写完 - - ！
==========================