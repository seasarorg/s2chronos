
CREATE TABLE TRIGGER(
	ID					BIGINT			NOT NULL PRIMARY KEY,	-- トリガーID(PK)
	NAME				VARCHAR(255),							-- トリガー名
	DESCRIPTION			VARCHAR(255),							-- 説明
	EXECUTED			BOOLEAN			NOT NULL DEFAULT 0,		-- 実行状態(実行中=true,停止中=false)
	EXEC_TYPE			CHAR(2)			NOT NULL,				-- TT = Timed, CT = Cron, DT = Delay, NT = NonDelay
	DELAY_TIME			BIGINT,									-- DT時の遅延時間			
	CRON_EXPRESSION		VARCHAR(255),							-- CT時のCRONコマンド
	START_TIME			TIMESTAMP,								-- TT時の開始予定時間 (CT時はCRON_EXPRESSION)
	END_TIME			TIMESTAMP,								-- TT時の終了予定時間
	VESRION_NO			DECIMAL
);

CREATE TABLE CRON_EXPRESSION(
	ID				BIGINT				NOT NULL,				-- トリガーID(PK)
	START_TIME		TIMESTAMP			NOT NULL,				-- 開始予定時間
	VESRION_NO		DECIMAL
	FOREIGN KEY(ID) REFERENCES TRIGGER(ID),
	PRIMARY KEY(ID, START_TIME)
);

CREATE TABLE TRIGGER_LOG(
	ID					BIGINT			NOT NULL PRIMARY KEY,	-- トリガーID(PK)
	NAME				VARCHAR(255),							-- トリガー名
	DESCRIPTION			VARCHAR(255),							-- 説明
	EXECUTED			BOOLEAN			NOT NULL DEFAULT 0,		-- 実行状態(実行中=true,停止中=false)
	EXEC_TYPE			CHAR(2)			NOT NULL,				-- TT = Timed, CT = Cron, DT = Delay, NT = NonDelay
	DELAY_TIME			BIGINT,									-- DT時の遅延時間			
	CRON_EXPRESSION		VARCHAR(255),							-- CT時のCRONコマンド
	START_TIME			TIMESTAMP,								-- TT時の開始予定時間 (CT時はCRON_EXPRESSION)
	END_TIME			TIMESTAMP,								-- TT時の終了予定時間
	VESRION_NO			DECIMAL,
	PRIMARY KEY(ID, START_TIME)
);