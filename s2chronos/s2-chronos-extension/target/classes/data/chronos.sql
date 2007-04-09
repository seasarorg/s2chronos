
DROP TABLE IF EXISTS SCHEDULE;
CREATE TABLE SCHEDULE(
	ID					INT				NOT NULL,
	NAME				VARCHAR(255),
	DESCRIPTION			VARCHAR(255),
	TASK_ID				INT				NOT NULL,
	STATUS				INT				NOT NULL,
	CREATE_DATE			TIMESTAMP,
	UPDATE_DATE			TIMESTAMP,
	VESRION_NO			DECIMAL,
	FOREIGN KEY(TASK_ID) REFERENCES TASK(ID),
	PRIMARY KEY(ID, STATUS)
);

DROP TABLE IF EXISTS SCHEDULE_LOG;
CREATE TABLE SCHEDULE_LOG(
	ID					INT				NOT NULL,
	NAME				VARCHAR(255),
	DESCRIPTION			VARCHAR(255),
	TASK_ID				INT				NOT NULL,
	LAST_STATUS			INT				NOT NULL,
	CREATE_DATE			TIMESTAMP,
	UPDATE_DATE			TIMESTAMP,
	VESRION_NO			DECIMAL,
	PRIMARY KEY(ID, LAST_STATUS)
);

DROP TABLE IF EXISTS TRIGGER;
CREATE TABLE TRIGGER(
	ID					INT				NOT NULL PRIMARY KEY,	-- トリガーID(PK)
	NAME				VARCHAR(255),							-- トリガー名
	DESCRIPTION			VARCHAR(255),							-- 説明
	EXECUTE				BOOLEAN			NOT NULL DEFAULT 0,		-- 実行状態(実行中=true,停止中=false)
	EXEC_TYPE			CHAR(2)			NOT NULL,				-- TT = Timed, CT = Cron, DT = Delay, NT = NonDelay
	DELAY_TIME			BIGINT,									-- DT時の遅延時間			
	CRON_EXPRESSION		VARCHAR(255),							-- CT時のCRONコマンド
	START_TIME			TIMESTAMP,								-- TT時の開始予定時間 (CT時はCRON_EXPRESSION)
	END_TIME			TIMESTAMP,								-- TT時の終了予定時間
	VESRION_NO			DECIMAL
);

DROP TABLE IF EXISTS TRIGGER_LOG;
CREATE TABLE TRIGGER_LOG(
	ID					INT				NOT NULL PRIMARY KEY,	-- トリガーID(PK)
	NAME				VARCHAR(255),							-- トリガー名
	DESCRIPTION			VARCHAR(255),							-- 説明
	EXECUTE				BOOLEAN			NOT NULL DEFAULT 0,		-- 実行状態(実行中=true,停止中=false)
	EXEC_TYPE			CHAR(2)			NOT NULL,				-- TT = Timed, CT = Cron, DT = Delay, NT = NonDelay
	DELAY_TIME			INT,									-- DT時の遅延時間			
	CRON_EXPRESSION		VARCHAR(255),							-- CT時のCRONコマンド
	START_TIME			TIMESTAMP,								-- TT時の開始予定時間 (CT時はCRON_EXPRESSION)
	END_TIME			TIMESTAMP,								-- TT時の終了予定時間
	VESRION_NO			DECIMAL
);

DROP TABLE IF EXISTS CRON_EXPRESSION;
CREATE TABLE CRON_EXPRESSION(
	ID				INT					NOT NULL,				-- トリガーID(PK)
	START_TIME		TIMESTAMP			NOT NULL,				-- 開始予定時間
	VESRION_NO		DECIMAL,
	FOREIGN KEY(ID) REFERENCES TRIGGER(ID),
	PRIMARY KEY(ID, START_TIME)
);

DROP TABLE IF EXISTS CRON_EXPRESSION_LOG;
CREATE TABLE CRON_EXPRESSION_LOG(
	ID				INT					NOT NULL,				-- トリガーID(PK)
	START_TIME		TIMESTAMP			NOT NULL,				-- 開始予定時間
	VESRION_NO		DECIMAL,
	FOREIGN KEY(ID) REFERENCES TRIGGER_LOG(ID),
	PRIMARY KEY(ID, START_TIME)
);

DROP TABLE IF EXISTS THREAD_POOL;
CREATE TABLE THREAD_POOL(
	ID					INT				NOT NULL PRIMARY KEY,
	NAME				VARCHAR(255),
	DESCRIPTION			VARCHAR(255),
	THREAD_POOL_TYPE	INT				NOT NULL,
	THREAD_POOL_SIZE	INT,
	VESRION_NO			DECIMAL
);

DROP TABLE IF EXISTS THREAD_POOL_LOG;
CREATE TABLE THREAD_POOL_LOG(
	ID					INT				NOT NULL PRIMARY KEY,
	NAME				VARCHAR(255),
	DESCRIPTION			VARCHAR(255),
	THREAD_POOL_TYPE	INT				NOT NULL,
	THREAD_POOL_SIZE	INT,
	VESRION_NO			DECIMAL
);

DROP TABLE IF EXISTS TASK;
CREATE TABLE TASK(
	ID					INT				NOT NULL PRIMARY KEY,
	NAME				VARCHAR(255),
	DESCRIPTION			VARCHAR(255),
	THREAD_POOL_ID		INT,
	THREAD_POOL_TYPE	INT,
	THREAD_POOL_SIZE	INT,
	TRIGGER_ID			INT,
	EXECUTE				BOOLEAN,
	START_TASK			BOOLEAN,
	END_TASK			BOOLEAN,
	SHUTDOWN_TASK		BOOLEAN,
	VESRION_NO			DECIMAL,
	FOREIGN KEY(THREAD_POOL_ID) REFERENCES THREAD_POOL(ID),
	FOREIGN KEY(TRIGGER_ID) REFERENCES TRIGGER(ID)
);

DROP TABLE IF EXISTS TASK_LOG;
CREATE TABLE TASK_LOG(
	ID					INT				NOT NULL PRIMARY KEY,
	NAME				VARCHAR(255),
	DESCRIPTION			VARCHAR(255),
	THREAD_POOL_LOG_ID	INT,
	THREAD_POOL_TYPE	INT,
	THREAD_POOL_SIZE	INT,
	TRIGGER_LOG_ID		INT,
	EXECUTE				BOOLEAN,
	START_TASK			BOOLEAN,
	END_TASK			BOOLEAN,
	SHUTDOWN_TASK		BOOLEAN,
	VESRION_NO			DECIMAL,
	FOREIGN KEY(THREAD_POOL_LOG_ID) REFERENCES THREAD_POOL_LOG(ID),
	FOREIGN KEY(TRIGGER_LOG_ID) REFERENCES TRIGGER_LOG(ID)
);

