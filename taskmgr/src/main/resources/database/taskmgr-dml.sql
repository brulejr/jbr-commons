INSERT INTO T_TASK_STATUS (TASK_status) VALUES ('CREATED');
INSERT INTO T_TASK_STATUS (TASK_status) VALUES ('STARTED');
INSERT INTO T_TASK_STATUS (TASK_status) VALUES ('HELD');
INSERT INTO T_TASK_STATUS (TASK_status) VALUES ('COMPLETED');
INSERT INTO T_TASK_STATUS (TASK_status) VALUES ('ERROR');

INSERT INTO T_TASK (task_uuid, task_name, task_desc, task_status) VALUES (UUID(UUID()), 'Task #1', 'First Task', 'CREATED');
INSERT INTO T_TASK (task_uuid, task_name, task_desc, task_status) VALUES (UUID(UUID()), 'Task #2', 'Second Task', 'CREATED');
INSERT INTO T_TASK (task_uuid, task_name, task_desc, task_status) VALUES (UUID(UUID()), 'Task #3', 'Third Task', 'STARTED');