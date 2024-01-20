-- noinspection SqlNoDataSourceInspectionForFile

-- Insert roles
-- INSERT INTO role (id, description) VALUES (1, 'STUDENT');
-- INSERT INTO role (id, description) VALUES (2, 'RESEARCHER');
-- INSERT INTO role (id, description) VALUES (3, 'EXPERT');
-- already done in RoleService

-- Insert students
-- Replace 'password' with the actual hashed password, if your system uses password hashing
-- The birthDate format should match your database date format, typically 'YYYY-MM-DD'
INSERT INTO users (DTYPE, first_name, last_name, email, username, password, active, status, birth_date) VALUES ('Student', 'John', 'Doe', 'john.doe@example.com', 'johndoe', '$2a$10$lO6.hETkU6HMx71srMUJpuRi3P6fMC4/qMyqXebMFrfkerjQ4QHVy', true, 0, '2000-01-01');
INSERT INTO users (DTYPE, first_name, last_name, email, username, password, active, status, birth_date) VALUES ('Student', 'Jane', 'Smith', 'jane.smith@example.com', 'janesmith', '$2a$10$lO6.hETkU6HMx71srMUJpuRi3P6fMC4/qMyqXebMFrfkerjQ4QHVy', true, 0, '2001-02-02');


-- Insert user-role mappings
INSERT INTO userrole (iduser, idrole) VALUES (1, 1); -- John Doe as STUDENT
INSERT INTO userrole (iduser, idrole) VALUES (1, 2); -- John Doe as RESEARCHER
INSERT INTO userrole (iduser, idrole) VALUES (2, 3); -- Jane Smith as EXPERT
