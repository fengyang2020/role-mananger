# 验证测试说明

### 一、指定信息持久化目录
编辑src/main/resources/application.properties
~~~ properties
user-info.dir=/path/userInfoDir
~~~
跑单测注意修改测试目录下的对应配置项目

### 二、Postman测试
可用postman导入项目根目录的文件: RoleManager.postman_collection.json

### 三、用户header信息
http请求header中用户信息key为role_info


##### 1.1 测试admin
~~~ JSON
{
"userId":123456,
"accountName": "Jerry",
"role": "admin"
}
~~~
对应http中header的role_info值为:
~~~
eyJ1c2VySWQiOjEyMzQ1NiwiYWNjb3VudE5hbWUiOiJYWFhYWFhYIiwicm9sZSI6ImFkbWluIn0K
~~~



##### 1.2 测试user1
~~~ JSON
{
"userId":213456,
"accountName": "Tom",
"role": "user"
}
~~~
对应http中header的role_info值为:
~~~ bash
ewoidXNlcklkIjoyMTM0NTYsCiJhY2NvdW50TmFtZSI6ICJUb20iLAoicm9sZSI6ICJ1c2VyIgp9
~~~



##### 1.3 测试user2
~~~ JSON
{
"userId":3846620,
"accountName": "Kevin",
"role": "user"
}
~~~
对应http中header的role_info值为:
~~~ bash
ICB7CiAgICAidXNlcklkIjogMzg0NjYyMCwKICAgICJhY2NvdW50TmFtZSI6ICJLZXZpbiIsCiAgICAicm9sZSI6ICJ1c2VyIgogIH0=
~~~

##### 1.4 测试不存在用户
~~~ JSON
{
"userId":9999,
"accountName": "NoSuchUser",
"role": "user"
}
~~~
对应http中header的role_info值为:
~~~ bash
ewoidXNlcklkIjo5OTk5LAoiYWNjb3VudE5hbWUiOiAiTm9TdWNoVXNlciIsCiJyb2xlIjogInVzZXIiCn0=
~~~



