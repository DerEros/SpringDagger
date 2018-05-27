DROP TABLE IF EXISTS pizza, ingredients, pizza_ingredients;

CREATE TABLE IF NOT EXISTS ingredients (
  id              INT PRIMARY KEY,
  ingredient_type VARCHAR,
  name            VARCHAR,
  price           FLOAT
);
CREATE TABLE IF NOT EXISTS pizza (
  id   INT PRIMARY KEY,
  name VARCHAR,
);
CREATE TABLE IF NOT EXISTS pizza_ingredients (
  pizza_id       INT,
  ingredients_ID INT
);

INSERT INTO ingredients VALUES (1, 'DOUGH', 'Thin Dough', 1.0),
  (2, 'DOUGH', 'Thick Dough', 1.5),
  (3, 'SAUCE', 'Tomato Sauce', 0.5),
  (4, 'SAUCE', 'BBQ Sauce', 0.7),
  (5, 'TOPPING', 'Cheese', 0.0),
  (6, 'TOPPING', 'Salami', 0.5);

INSERT INTO pizza VALUES (1, 'Quattro Formaggi'),
  (2, 'Pizza Salami'),
  (3, 'Pizza BBQ'),
  (4, 'Pizza All-in');

INSERT INTO pizza_ingredients VALUES (1, 1), (1, 3), (1, 5),
  (2, 1), (2, 3), (2, 6),
  (3, 2), (3, 4), (3, 5),
  (4, 1), (4, 3), (4, 4), (4, 5), (4, 6);