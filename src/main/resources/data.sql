-- noinspection SqlNoDataSourceInspectionForFile

-- Insert roles
-- INSERT INTO role (id, description) VALUES (1, 'STUDENT');
-- INSERT INTO role (id, description) VALUES (2, 'RESEARCHER');
-- INSERT INTO role (id, description) VALUES (3, 'EXPERT');
-- already done in RoleService

-- Insert students
-- Replace 'password' with the actual hashed password, if your system uses password hashing
-- The birthDate format should match your database date format, typically 'YYYY-MM-DD'
INSERT INTO users (DTYPE, first_name, last_name, email, username, password, active, status, birth_date) VALUES ('Student', 'John', 'Doe', 'john.doe@example.com', 'johndoe', '$2a$10$lO6.hETkU6HMx71srMUJpuRi3P6fMC4/qMyqXebMFrfkerjQ4QHVy', true, 1, '2000-01-01');
INSERT INTO users (DTYPE, first_name, last_name, email, username, password, active, status, birth_date) VALUES ('Student', 'Jane', 'Smith', 'jane.smith@example.com', 'janesmith', '$2a$10$lO6.hETkU6HMx71srMUJpuRi3P6fMC4/qMyqXebMFrfkerjQ4QHVy', true, 1, '2001-02-02');


-- Insert user-role mappings
INSERT INTO userrole (iduser, idrole) VALUES (1, 1); -- John Doe as STUDENT
INSERT INTO userrole (iduser, idrole) VALUES (1, 2); -- John Doe as RESEARCHER
INSERT INTO userrole (iduser, idrole) VALUES (2, 3); -- Jane Smith as EXPERT



-- Insert 10 courses with different titles and descriptions
INSERT INTO course (title, description) VALUES ('Introduction to Mathematics', 'A beginner-friendly course covering basic mathematical concepts.');
INSERT INTO course (title, description) VALUES ('Fundamentals of Physics', 'Explore the fundamental principles of physics.');
INSERT INTO course (title, description) VALUES ('Basic Chemistry', 'An introductory course on chemical elements and compounds.');
INSERT INTO course (title, description) VALUES ('English Literature', 'A journey through classic and modern English literature.');
INSERT INTO course (title, description) VALUES ('History of Art', 'Explore the history and development of art.');
INSERT INTO course (title, description) VALUES ('Computer Science Basics', 'An introduction to fundamental concepts in computer science.');
INSERT INTO course (title, description) VALUES ('Learning Spanish for Beginners', 'A course designed for beginners to learn Spanish.');
INSERT INTO course (title, description) VALUES ('Introduction to Economics', 'Understanding the basics of economics.');
INSERT INTO course (title, description) VALUES ('Biology: Life and Evolution', 'Learn about the biological aspects of life and evolution.');
INSERT INTO course (title, description) VALUES ('Philosophy: Thinking and Reasoning', 'A course on the basics of philosophical thought.');


-- Insert 10 contents for the first course (course_id = 1)
-- relationship of courses to contents managed automatically. So inserting the course_id is enough to create the relationship
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Math Basics', 'text', 'Introduction to basic math concepts.', 1);
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Numbers and Operations', 'text', 'Exploring numbers and basic operations.', 1);
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Math in Daily Life', 'text', 'Podcast discussing the role of math in everyday life.', 1);
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Geometry Fundamentals', 'text', 'Geometry concepts and their applications.', 1);
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Algebra Introduction', 'text', 'An introductory guide to algebra.', 1);
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Statistics Basics', 'text', 'Video lessons on basic statistical methods.', 1);
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Calculus Overview', 'text', 'Understanding the basics of calculus.', 1);
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Mathematical Puzzles', 'text', 'Interactive puzzles to enhance math skills.', 1);
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Advanced Mathematical Theories', 'text', 'A deep dive into advanced math theories.', 1);
INSERT INTO content (title, content_type, content_data, course_id) VALUES ('Mathematics in Science', 'text', 'Exploring the role of math in scientific research.', 1);


