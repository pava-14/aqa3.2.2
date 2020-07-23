winpty docker container exec -it aqa321_mysql_1 mysql -u app -ppass app -e "SET FOREIGN_KEY_CHECKS = 0;TRUNCATE TABLE users;SET FOREIGN_KEY_CHECKS = 1;TRUNCATE TABLE cards;"
java -jar ./app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=pass



