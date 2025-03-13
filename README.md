# PINK_WEB

🖖硕士研究生工程实训项目前端架构：Pink招聘系统

本系统分为四大子系统：用户前台子系统、HR管理子系统、公司管理子系统、Admin子系统

前端有manage-system和front-desk两个文件夹，分别在两个文件夹下运行部署：

````shell
npm install
npm run dev
````

后端直接部署即可



recruitment_info中的账号登录hr系统

```
19204390805
```

entterprise_admin中的账号登录company系统

```
13572695813
```





在Windows系统中，如果8080端口被占用，可以通过以下步骤来关闭该端口：

步骤1：查找占用8080端口的进程

首先，打开命令提示符（以管理员身份运行），然后输入以下命令来查找占用8080端口的进程：

`netstat -o -n -a | findstr :8080`

该命令会显示所有正在监听或使用8080端口的进程，并显示它们的PID（进程ID）。例如，输出可能如下所示：

TCP 0.0.0.0:8080 0.0.0.0:0 LISTENING 22260

在这个例子中，PID为22260的进程正在占用8080端口[1](https://blog.csdn.net/qq_48556886/article/details/124432808)[2](https://blog.csdn.net/m0_64071068/article/details/123202471)。

步骤2：终止占用8080端口的进程

接下来，使用以下命令来终止占用8080端口的进程：

`taskkill /F /PID 22260`

将*22260*替换为上一步中找到的PID。这将强制终止该进程，并释放8080端口[1](https://blog.csdn.net/qq_48556886/article/details/124432808)[3](https://blog.csdn.net/lvshu_yuan/article/details/100025428)。

注意事项

在终止进程之前，请确保您不再需要该进程，并且终止该进程不会对您的系统或其他应用程序造成负面影响。如果您是在开发环境中使用8080端口，可能是其他应用程序或服务正在使用该端口。在这种情况下，您可以尝试更改您的应用程序或服务的端口号来避免冲突[2](https://blog.csdn.net/m0_64071068/article/details/123202471)。

通过以上步骤，您可以有效地关闭被占用的8080端口，并确保您的应用程序能够正常运行。



```mysql
CREATE TABLE message (
    m_id INT AUTO_INCREMENT PRIMARY KEY,
    c_id INT NOT NULL,
    r_id INT NOT NULL,
    m_content TEXT NOT NULL,
    m_time DATETIME NOT NULL,
    FOREIGN KEY (c_id) REFERENCES user(id),
    FOREIGN KEY (r_id) REFERENCES recruitment_info(ehrid)
);

# flag为0时表示已接受，为1表示已发送
 ALTER TABLE message ADD flag INT(1);
```

