ln -s /opt/jboss/server/test/log/full-server.log ./test_full.log

-Xms128m -Xmx512m


"-Denv=testEnv" "-Denv_path=online" "-Denv.num=01"




运行mvn install时跳过Test
mvn install -DskipTests
或者
mvn install -Dmaven.test.skip=true

mvn clean install
mvn install
mvn deploy

git remoter
https://github.com/jinny-c/jiddProject.git

--日志查询
grep "/ deployment failed" test_full.log
--文件查询
find / -name nginx.conf 2>/dev/null
