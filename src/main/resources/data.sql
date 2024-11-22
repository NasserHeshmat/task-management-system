INSERT INTO TASK (TITLE, DESCRIPTION, STATUS, PRIORITY, DUE_DATE)
VALUES ('Complete Project', 'Work on the final project tasks', 'TODO', 1, TO_DATE('2024-12-10', 'YYYY-MM-DD'));

INSERT INTO TASK (TITLE, DESCRIPTION, STATUS, PRIORITY, DUE_DATE)
VALUES ('Prepare Presentation', 'Create slides for the demo', 'IN_PROGRESS', 2, TO_DATE('2024-12-05', 'YYYY-MM-DD'));

INSERT INTO TASK (TITLE, DESCRIPTION, STATUS, PRIORITY, DUE_DATE)
VALUES ('Submit Report', 'Submit the final project report', 'DONE', 3, TO_DATE('2024-12-01', 'YYYY-MM-DD'));

-- Insert roles
INSERT INTO ROLE (NAME) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (NAME) VALUES ('ROLE_USER');

-- Insert users (passwords are hashed using BCrypt)
INSERT INTO USER_ACCOUNT (USERNAME, PASSWORD)
VALUES
    ('admin', '$2a$10$B1butp7SFbmAmZl/8tBG5u4nt4y0yuqs0WStEzusE5jP99q/oPphy'); -- Password: 1111
INSERT INTO USER_ACCOUNT (USERNAME, PASSWORD)
VALUES
    ('user', '$2a$10$B1butp7SFbmAmZl/8tBG5u4nt4y0yuqs0WStEzusE5jP99q/oPphy');  -- Password: 1111

-- Assign roles to users
INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES (1, 1); -- Admin role for admin
INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES (2, 2); -- User role for user
