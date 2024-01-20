-- noinspection SqlNoDataSourceInspectionForFile

-- Insert roles
-- INSERT INTO role (id, description) VALUES (1, 'STUDENT');
-- INSERT INTO role (id, description) VALUES (2, 'RESEARCHER');
-- INSERT INTO role (id, description) VALUES (3, 'EXPERT');

-- Insert students
-- Replace 'password' with the actual hashed password, if your system uses password hashing
-- The birthDate format should match your database date format, typically 'YYYY-MM-DD'
INSERT INTO users (id, DTYPE, first_name, last_name, email, username, password, active, status, birth_date) VALUES (2, 'Student', 'John', 'Doe', 'john.doe@example.com', 'johndoe', 'password', true, 0, '2000-01-01');
INSERT INTO users (id, DTYPE, first_name, last_name, email, username, password, active, status, birth_date) VALUES (3, 'Student', 'Jane', 'Smith', 'jane.smith@example.com', 'janesmith', 'password', true, 0, '2001-02-02');


-- Insert user-role mappings
INSERT INTO userrole (iduser, idrole) VALUES (1, 1); -- John Doe as STUDENT
INSERT INTO userrole (iduser, idrole) VALUES (1, 2); -- John Doe as RESEARCHER
INSERT INTO userrole (iduser, idrole) VALUES (2, 3); -- Jane Smith as EXPERT
