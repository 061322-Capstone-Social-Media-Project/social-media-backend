INSERT INTO users (email, password, first_name, last_name) VALUES (
    'testuser@gmail.com',
    'password',
    'Test',
    'User'
);
INSERT INTO users (email, password, first_name, last_name) VALUES (
  'testuser2@gmail.com',
  'password',
  'ab',
  'cd'
);

INSERT INTO posts (id, text, image_url, author_id) VALUES (
    10000,
    'The classic',
    'https://i.imgur.com/fhgzVEt.jpeg',
    1
),
(
    10001,
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
    '',
    1
); 

insert into users (email, first_name, last_name, password) values ('c@gmail.com', 'Calvin', 'Post', 'password');
insert into users (email, first_name, last_name, password) values ('a@gmail.com', 'Adam', 'Harbeck', 'password');
insert into users (email, first_name, last_name, password) values ('s@gmail.com', 'Shouchuang', 'Zhu', 'password');

insert into follower (follower_id, following_id) values (1, 2);
insert into follower (follower_id, following_id) values (1, 3);
insert into follower (follower_id, following_id) values (1, 4);
insert into follower (follower_id, following_id) values (1, 5);

insert into follower (follower_id, following_id) values (4, 1);
insert into follower (follower_id, following_id) values (5, 1);

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
