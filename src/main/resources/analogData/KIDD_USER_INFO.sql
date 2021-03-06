
--ORACLE
--用户信息表

CREATE TABLE KIDD_USER_INFO
(
	USER_ID    		VARCHAR2(20) 	CONSTRAINT PK_USER_INFO_ID PRIMARY KEY,   		-- ID，主键 
	USER_NAME  		VARCHAR2(20) 	NOT NULL UNIQUE,	-- 用户名
	REAL_NAME  		VARCHAR2(40),    			-- 姓名
	NICK_NAME  		VARCHAR2(80),    			-- 昵称
	RESERVED_FIELD  VARCHAR2(600),    			-- 备用
	CREAT_TIME    	VARCHAR2(14) DEFAULT TO_CHAR(SYSDATE,'YYYYMMDDHH24MMSS'),   -- 记录创建时间 
	UPD_TIME       	VARCHAR2(14) DEFAULT TO_CHAR(SYSDATE,'YYYYMMDDHH24MMSS')    -- 记录更新时间 
);

COMMENT ON TABLE KIDD_USER_INFO IS '用户信息表';

COMMENT ON COLUMN KIDD_USER_INFO.USER_ID    IS 'ID，主键';
COMMENT ON COLUMN KIDD_USER_INFO.USER_NAME  IS '用户名';
COMMENT ON COLUMN KIDD_USER_INFO.REAL_NAME    IS '姓名';
COMMENT ON COLUMN KIDD_USER_INFO.NICK_NAME       IS '昵称';
COMMENT ON COLUMN KIDD_USER_INFO.RESERVED_FIELD  IS '备用';
COMMENT ON COLUMN KIDD_USER_INFO.CREAT_TIME    IS '记录创建时间';
COMMENT ON COLUMN KIDD_USER_INFO.UPD_TIME       IS '记录更新时间';


--ALTER TABLE KIDD_USER_INFO ADD CONSTRAINT PK_USER_INFO_ID PRIMARY KEY (USER_ID);

--修改记录
ALTER TABLE KIDD_USER_INFO ADD USER_SEX CHAR(1) DEFAULT 'M';	-- 性别：M-MALE-男，F-FEMALE-女
COMMENT ON COLUMN KIDD_USER_INFO.USER_SEX       IS '性别：M-MALE-男，F-FEMALE-女';
