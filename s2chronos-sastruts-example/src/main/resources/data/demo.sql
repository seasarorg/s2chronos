DROP TABLE EVENT ;
CREATE TABLE EVENT(
EVENT_ID IDENTITY PRIMARY KEY,
EVENT_DATE TIMESTAMP,
EVENT_TEXT VARCHAR(255),
EVENT_STATUS INT
);