# 需要java8这个镜像
FROM java:8
# 暴露8080端口
EXPOSE 8080
# 自定义环境变量
ENV DB_HOST 127.0.0.1
# 打包文件并命名
ADD target/app.jar app.jar
# 入口命令
ENTRYPOINT ["java", "-jar", "/app.jar"]