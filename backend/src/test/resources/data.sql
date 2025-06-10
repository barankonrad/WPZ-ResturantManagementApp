INSERT INTO roles (name)
VALUES ('ADMIN'),
       ('WAITER'),
       ('CHEF'),
       ('USER');

INSERT INTO users (email, password, role_id)
VALUES ('admin@wpz.com', '$2a$10$GAx8hTIln.coEwaiOfD.vunA26LHO3jB27co.9lfOcYwV.Yy5eJwK', 1),
       ('waiter@wpz.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 2),
       ('chef@wpz.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 3);


INSERT INTO menu_items (name, description, price, is_available)
VALUES ('Margherita Pizza', 'Classic Italian pizza with tomatoes, mozzarella, and basil', 12.99, true);
