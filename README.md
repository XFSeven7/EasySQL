# EasySQL
使用起来很简单的sql工具

初始化EasySQL

```
EasySQL.init(context);
```

创建数据库

```
EasySQL.createDB(String dbname)不用加.db
```



创建表

```
EasySQL.createTable(class<? extend EasyTable>) 默认带ID
EasySQL.createTable(class<? extend EasyTable>, boolean hasID) 是否携带ID
```



删除表

```
EasySQL.use(String dbname).deleteTable(class<? extend EasyTable>) 删除某数据库中某表
```

增删改查 某数据库中某表记录

```
EasySQL.use(String dbname).save(new class<? extend EasyTable>) 添加某表记录
EasySQL.use(String dbname).delete(new class<? extend EasyTable>) 删除某表记录
EasySQL.use(String dbname).update(new class<? extend EasyTable>, int id) 更新某表的某条记录
EasySQL.use(String dbname).update(new class<? extend EasyTable>, int id) 更新某表的某条记录
new class<? extend EasyTable> = EasySQL.use(String dbname).check(new class<? extend EasyTable>) 查询某表记录
Arrlist<BD> =  EasySQL.listDB() 获取数据库列表
Arrlist<Table> = EasySQL.use(String name).listTable() 获取某数据库所有表
table.getDB 通过表实体得到属于哪个数据库
Arrlist<DB> = table.getDB 也许在多个数据库中存在相同的表
```


不过该库还没有写好呢，鬼知道多久才能写完 - - ！
==========================