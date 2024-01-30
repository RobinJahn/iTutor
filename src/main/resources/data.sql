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

-- Insert new users for a sample chat
INSERT INTO users (DTYPE, first_name, last_name, email, username, password, active, status, birth_date) VALUES ('Student', 'Frodo', 'Baggins', 'frodo.baggins@example.com', 'ringcarrier', '$2a$10$lO6.hETkU6HMx71srMUJpuRi3P6fMC4/qMyqXebMFrfkerjQ4QHVy', true, 0, '2001-03-03');
INSERT INTO users (DTYPE, first_name, last_name, email, username, password, active, status, birth_date) VALUES ('Student', 'Bilbo', 'Baggins', 'bilbo.baggins@example.com', 'discoverer', '$2a$10$lO6.hETkU6HMx71srMUJpuRi3P6fMC4/qMyqXebMFrfkerjQ4QHVy', true, 0, '2001-04-04');
INSERT INTO users (DTYPE, first_name, last_name, email, username, password, active, status, birth_date) VALUES ('Student', 'Peregrin', 'Took', 'peregrin.took@example.com', 'pippin', '$2a$10$lO6.hETkU6HMx71srMUJpuRi3P6fMC4/qMyqXebMFrfkerjQ4QHVy', true, 0, '2001-05-05');


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


-- Insert sample data for the statistics overview
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-23', 'Course Creation', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Creation', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Creation', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Creation', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Creation', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-21', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Creation', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Creation', 'MuchProvider2');

INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-23', 'Course Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-21', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Course Viewing', 'MuchProvider2');

INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-23', 'Content Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Content Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Content Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Content Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Content Viewing', 'ContentMachine1');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-21', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-24', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Content Viewing', 'MuchProvider2');
INSERT INTO user_activity (date, activity_type, username) VALUES ('2024-01-25', 'Content Viewing', 'MuchProvider2');


--Insert Tests
INSERT INTO test (testName) VALUES ('Mathematics');
INSERT INTO test (testName) VALUES ('Physics');
INSERT INTO test (testName) VALUES ('Chemistry');
INSERT INTO test (testName) VALUES ('Spanish');

INSERT INTO question(question, test_id) VALUES ('Let the equation be given: 2x+5=15. Solve the equation and determine the value of x.',1);


-- Insert Chat Messages for a sample chat between bilbo and frodo baggins
-- Frodo's first message
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'ringcarrier', 'discoverer', 'Hello, Uncle Bilbo! I was wondering if you could help me understand more about history.', '2024-01-30T17:00:21.521+00:00');

-- Bilbo's first response
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'discoverer', 'ringcarrier', 'Of course, Frodo! History is a fascinating subject. It''s much like a vast, unending story, filled with tales of peoples and places, much like our own Middle-earth.', '2024-01-30T17:01:00.521+00:00');

-- Frodo's second message
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'ringcarrier', 'discoverer', 'I''ve always loved your stories about the Elves and Dwarves. But how does learning history help us?', '2024-01-30T17:01:40.521+00:00');

-- Bilbo's second response
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'discoverer', 'ringcarrier', 'Ah, history teaches us about the successes and failures of those before us. Remember the downfall of Númenor? It was caused by the pride and folly of its people, a valuable lesson against the lure of power and immortality.', '2024-01-30T17:02:20.521+00:00');

-- Frodo's third message
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'ringcarrier', 'discoverer', 'That sounds similar to the Ring''s history. The one you found on your adventures.', '2024-01-30T17:03:00.521+00:00');

-- Bilbo's third response
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'discoverer', 'ringcarrier', 'Precisely! The Ring''s history is a grim reminder of how power can corrupt. It was created by Sauron, a servant of Morgoth, in the fires of Mount Doom to control the other Rings of Power.', '2024-01-30T17:03:40.521+00:00');

-- Frodo's fourth message
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'ringcarrier', 'discoverer', 'So, by studying history, we can learn from past mistakes?', '2024-01-30T17:04:20.521+00:00');

-- Bilbo's fourth response
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'discoverer', 'ringcarrier', 'Exactly, Frodo. And not just mistakes, but also wisdom and courage. Take the Last Alliance of Elves and Men; they united despite their differences to fight a common enemy.', '2024-01-30T17:05:00.521+00:00');

-- Frodo's fifth message
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'ringcarrier', 'discoverer', 'Like how we need to work together in the present, regardless of our backgrounds.', '2024-01-30T17:05:40.521+00:00');

-- Bilbo's fifth response
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'discoverer', 'ringcarrier', 'Indeed! History also teaches us about cultures and customs, like the Ents of Fangorn Forest, ancient beings who remind us to respect nature.', '2024-01-30T17:06:20.521+00:00');

-- Frodo's sixth message
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'ringcarrier', 'discoverer', 'It''s all so interconnected, isn''t it? History, our actions, and the future.', '2024-01-30T17:07:00.521+00:00');

-- Bilbo's sixth response
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'discoverer', 'ringcarrier', 'Absolutely. The scrolls of the Elves, the annals of the Dwarves, and even the tales of Hobbits like us, all contribute to the grand tapestry of history.', '2024-01-30T17:07:40.521+00:00');

-- Frodo's seventh message
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'ringcarrier', 'discoverer', 'Thank you, Uncle Bilbo. I see now how important history is. It''s not just about dates and events, but about understanding our world and our place in it.', '2024-01-30T17:08:20.521+00:00');

-- Bilbo's closing message
INSERT INTO chat_message (chat_id, sender_id, recipient_id, content, timestamp) VALUES ('ringcarrier_discoverer', 'discoverer', 'ringcarrier', 'Well said, Frodo! Remember, history is not only about learning what happened; it''s about understanding why it happened and how it shapes our journey forward. Now, let''s have some tea and I''ll tell you more about the Elves of Rivendell...', '2024-01-30T17:09:00.521+00:00');



