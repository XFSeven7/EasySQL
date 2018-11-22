# EasySQL
使用起来很简单的sql工具，支持链式操作

使用方法
--

 - **gradle**

第一步：将其添加到存储库末尾的根build.gradle中：
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
第二步：添加到依赖

```
dependencies {
     implementation 'com.github.qixuefeng:EasySQL:0.2.0'
}
```

新增数据库


数据库操作
---

 - **创建数据库**

```
EasySQL.with(context).createDB(dbName); // 不用加.db
```

 - **删除数据库**

```
EasySQL.with(context).deleteDatabase(dbName);
```

 - **使用指定数据库**

```
EasySQL.with(context).use(dbName);
```

 - **获取数据库列表**

```
EasySQL.with(context).listName();
```

 - **获取某数据库中所有表**

```
EasySQL.with(context).use(dbName).tableList()
```

 - **更新数据库**（给数据库中的表增加字段）

使用步骤如下，

假设原来的表为：

```
public class NormalTable1 extends EasyTable {
    private String name;
    private int age;
}
```
若要新增字段，直接在原来的表中新增字段：

```
public class NormalTable1 extends EasyTable {
    private String name;
    private int age;
    private int newField;
}
```
之后只需调用

```
// 更新所有数据库中的所有表
EasySQL.with(this).updateAllTable();
```
或者

```
// 只更新某个数据库中的所有表：
EasySQL.with(this).updateAllTable(dbName);
```

表操作
---

 - **创建表**

表实体需继承EasyTable，并且需要有一个**无参构造方法**，目前支持的数据类型有以下8种：

```
byte
long
float
short
byte[]
double
String
boolean
int
```
具体使用：
```
EasySQL.with(context).use(dbName).createTable(表实体.class) // 默认带自增长ID
EasySQL.with(context).use(dbName).createTable(表实体.class, boolean hasID) // 是否携带ID
EasySQL.with(context).use(dbName).createTable(表实体.class, 表实体.class, 表实体.class, ... ) // 是否携带ID
```

 - **删除表**

```
EasySQL.with(context).use(dbName).deleteTable(TypeEntity.class); 删除某数据库中某表
```

增删改查
----

 - **增**
新增的数据保存在EasyEntity实体类中，支持单个实体添加或者集合添加，如果检测到没有该表，则会自动创建表
	```
	// 方式1
	EasyEntity entity = new EasyEntity();
	Table1 table1 = new Table1(); // 各种set
	table1EasyEntity.add(table1);
	EasySQL.with(context).use(dbName).save(entity);

	// 方式2
	Table1 table1 = new Table1();
	EasySQL.with(context).use(dbName).save(table1);
	```

 - **删**

```
// 后面的两个参数跟Android自带的参数用法基本一样
EasySQL.with(context).use(dbName).delete(Table1.class, "_short = ?", "2");

// 清空表中所有数据
EasySQL.with(context).use(dbName).clear(Table1.class);
```

 - **改**

```
//更新某表的某条记录
EasySQL.with(this).use(dbName).update(typeEntity1, "_short = ?", "2");
```

 - **查**

```
// 查询表（Table1）中的所有数据
ArrayList<Table1> retrieve1 = EasySQL.with(context).use(trim).retrieve(Table1.class);
```

排序查询

```
// 排序查询 升序查询 指定使用哪个字段使用升序查询
ArrayList<NormalTable1> retrieve1 = EasySQL.with(this).use(dbName).retrieve(NormalTable1.class, "age");

// 排序查询 ，retrieve(Class<T> 查询表, String 排序字段, boolean 是否升序，false为降序查询)
ArrayList<NormalTable1> retrieve1 = EasySQL.with(this).use(dbName).retrieve(NormalTable1.class, "age", true);
```

最后
--
在使用上遇到什么问题或者有什么好的建议 欢迎联系我，或者发布Issues。