CREATE TABLE users (
  id bigserial primary key,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  display_name VARCHAR(255) NOT NULL,
  telephone VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

CREATE TABLE roles (
  id bigserial primary key,
  name VARCHAR(255) NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

CREATE TABLE categories (
  id         bigserial primary key,
  name VARCHAR(255) NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

CREATE TABLE advertisements (
  id         bigserial primary key,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  user_price NUMERIC(11, 2) NOT NULL,
  is_paid BOOLEAN NOT NULL,
  is_deleted BOOLEAN NOT NULL,
  expiration_date TIMESTAMP,
  user_id BIGINT,
  category_id BIGINT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE payments (
  id         bigserial primary key,
  user_id BIGINT,
  advertisement_id BIGINT,
  date TIMESTAMP,
  description VARCHAR(255) NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (advertisement_id) REFERENCES advertisements(id)
);

CREATE TABLE users_roles (
  user_id BIGINT,
  role_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO users (username, password, display_name, telephone, email, created_at, updated_at)
VALUES ('john_doe', '$2a$10$d9SaYo0LnMh2zp3rhuVTqOIGRnBy3VSMxnXpCopIPvBlMaxtoWBOu', 'John America', '+1 505-JOHN', 'john.doe@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Bob', '$2a$10$d9SaYo0LnMh2zp3rhuVTqOIGRnBy3VSMxnXpCopIPvBlMaxtoWBOu', 'ElvenRogue66', '+995 (587) 78-96-30', 'Bob@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO roles (name, created_at, updated_at)
VALUES ('ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
VALUES ('ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 1);

INSERT INTO categories (name, created_at, updated_at)
VALUES ('Electronics', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Clothing', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Furniture', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO advertisements (title, description, user_price, is_paid, is_deleted, expiration_date, user_id, category_id, created_at, updated_at)
VALUES ('iPhone X', 'Brand new iPhone X', 999.99, true, false, '2099-01-01 01:00:00', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Sony TV', '55" Smart TV', 799.99, true, false, '2099-01-01 01:00:00', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Tomato', 'Imported from Turkey', 9.99, false, true, CURRENT_TIMESTAMP, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Potato', 'Support your local farmer', 3.99, true, true, CURRENT_TIMESTAMP, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Leather Jacket', 'Stylish leather jacket', 199.99, false, false, '2099-01-01 01:00:00', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO payments (user_id, advertisement_id, date, description, created_at, updated_at)
VALUES (1, 1, CURRENT_TIMESTAMP, 'Payment for iPhone X', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, 2, CURRENT_TIMESTAMP, 'Payment for Sony TV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);