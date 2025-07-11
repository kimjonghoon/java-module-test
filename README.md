# Java Module Test

## Database Design for Oracle

Download a SCOTT schema from
https://github.com/oracle/dotnet-db-samples/blob/master/schemas/scott.sql

    sqlplus / as sysdba
    
    @C:\Users\john\Downloads\scott.sql
    
    sqlplus scott/TIGER
	alter user scott identified by tiger;
	exit;

## Database Design for MySQL

	mysql --user=root --password mysql
	
	create user 'scott'@'%' identified by 'tiger';
	grant all privileges on *.* to 'scott'@'%';
	
	create database xe;
	exit;
	
	mysql --user=scott --password xe
	
	CREATE TABLE DEPT (
	    DEPTNO DECIMAL(2),
	    DNAME VARCHAR(14),
	    LOC VARCHAR(13),
	    CONSTRAINT PK_DEPT PRIMARY KEY (DEPTNO) 
	);
	CREATE TABLE EMP (
	    EMPNO DECIMAL(4),
	    ENAME VARCHAR(10),
	    JOB VARCHAR(9),
	    MGR DECIMAL(4),
	    HIREDATE DATE,
	    SAL DECIMAL(7,2),
	    COMM DECIMAL(7,2),
	    DEPTNO DECIMAL(2),
	    CONSTRAINT PK_EMP PRIMARY KEY (EMPNO),
	    CONSTRAINT FK_DEPTNO FOREIGN KEY (DEPTNO) REFERENCES DEPT(DEPTNO)
	);
	CREATE TABLE SALGRADE ( 
	    GRADE TINYINT,
	    LOSAL SMALLINT,
	    HISAL SMALLINT 
	);
	INSERT INTO DEPT VALUES (10,'ACCOUNTING','NEW YORK');
	INSERT INTO DEPT VALUES (20,'RESEARCH','DALLAS');
	INSERT INTO DEPT VALUES (30,'SALES','CHICAGO');
	INSERT INTO DEPT VALUES (40,'OPERATIONS','BOSTON');
	INSERT INTO EMP VALUES (7369,'SMITH','CLERK',7902,STR_TO_DATE('17-12-1980','%d-%m-%Y'),800,NULL,20);
	INSERT INTO EMP VALUES (7499,'ALLEN','SALESMAN',7698,STR_TO_DATE('20-2-1981','%d-%m-%Y'),1600,300,30);
	INSERT INTO EMP VALUES (7521,'WARD','SALESMAN',7698,STR_TO_DATE('22-2-1981','%d-%m-%Y'),1250,500,30);
	INSERT INTO EMP VALUES (7566,'JONES','MANAGER',7839,STR_TO_DATE('2-4-1981','%d-%m-%Y'),2975,NULL,20);
	INSERT INTO EMP VALUES (7654,'MARTIN','SALESMAN',7698,STR_TO_DATE('28-9-1981','%d-%m-%Y'),1250,1400,30);
	INSERT INTO EMP VALUES (7698,'BLAKE','MANAGER',7839,STR_TO_DATE('1-5-1981','%d-%m-%Y'),2850,NULL,30);
	INSERT INTO EMP VALUES (7782,'CLARK','MANAGER',7839,STR_TO_DATE('9-6-1981','%d-%m-%Y'),2450,NULL,10);
	INSERT INTO EMP VALUES (7788,'SCOTT','ANALYST',7566,STR_TO_DATE('13-7-1987','%d-%m-%Y')-85,3000,NULL,20);
	INSERT INTO EMP VALUES (7839,'KING','PRESIDENT',NULL,STR_TO_DATE('17-11-1981','%d-%m-%Y'),5000,NULL,10);
	INSERT INTO EMP VALUES (7844,'TURNER','SALESMAN',7698,STR_TO_DATE('8-9-1981','%d-%m-%Y'),1500,0,30);
	INSERT INTO EMP VALUES (7876,'ADAMS','CLERK',7788,STR_TO_DATE('13-7-1987', '%d-%m-%Y'),1100,NULL,20);
	INSERT INTO EMP VALUES (7900,'JAMES','CLERK',7698,STR_TO_DATE('3-12-1981','%d-%m-%Y'),950,NULL,30);
	INSERT INTO EMP VALUES (7902,'FORD','ANALYST',7566,STR_TO_DATE('3-12-1981','%d-%m-%Y'),3000,NULL,20);
	INSERT INTO EMP VALUES (7934,'MILLER','CLERK',7782,STR_TO_DATE('23-1-1982','%d-%m-%Y'),1300,NULL,10);
	INSERT INTO SALGRADE VALUES (1,700,1200);
	INSERT INTO SALGRADE VALUES (2,1201,1400);
	INSERT INTO SALGRADE VALUES (3,1401,2000);
	INSERT INTO SALGRADE VALUES (4,2001,3000);
	INSERT INTO SALGRADE VALUES (5,3001,9999);
	COMMIT;
	exit;

## Have to do

Download the Oracle JDBC driver(ojdbc17.jar) from the https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html

Download the MySQL JDBC driver(mysql-connector-j-9.3.0.jar) from the https://dev.mysql.com/downloads/connector/j/

Add ojdbc17.jar, mysql-connector-j-9.3.0.jar to the jars/ directory.

## Compile

	javac -d out --module-source-path src $(find src -name "*.java")

## Copy the Java property files into each module directory

    cp src/net.java_school.db.dbpool.oracle/oracle.properties out/net.java_school.db.dbpool.oracle/
    cp src/net.java_school.db.dbpool.mysql/mysql.properties out/net.java_school.db.dbpool.mysql/
    
## Run

	java -p out:jars -m main.app/net.java_school.test.GetEmp
	
##	for using db.dbpool.mysql

    vi src/main.app/net/java_school/test/GetEmp.java
    
    ConnectionManageable manager = managers.stream().
        filter(provider -> isMySQL(provider.type())).
        map(ServiceLoader.Provider::get).findAny().get();

    javac -d out --module-source-path src $(find src -name "*.java")
    
    java -p out:jars -m main.app/net.java_school.test.GetEmp

    
    
