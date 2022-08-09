insert into users
(email, password, first_name, last_name, profile_pic, username, professional_url, location, name_pronunciation)
values 
('ftest@gmail.com', 'pass123', 'First', 'Test', 'https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-512.png', 'ftest', 'http://google.com/', 'Washington, D.C.', 'First Test');



insert into hobbies (user_id,hobby_1,hobby_2,hobby_3) values ('1', 'Sports', 'Games', 'Fishing');

-- INSERT INTO posts (id, text, image_url, author_id) VALUES (
--     10000,
--     'The classic',
--     'https://i.imgur.com/fhgzVEt.jpeg',
--     1
-- ),
-- (
--     10001,
--     'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
--     '',
--     1
-- ); 