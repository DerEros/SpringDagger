DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS special_offers;

CREATE TABLE IF NOT EXISTS ingredients (
  id              INT PRIMARY KEY,
  ingredient_type VARCHAR,
  name            VARCHAR,
  price           FLOAT
);
CREATE TABLE IF NOT EXISTS special_offers (
  id             INT PRIMARY KEY,
  ingredients_id INT,
  description    VARCHAR,
  details        VARCHAR
);

INSERT INTO ingredients VALUES (1, 'DOUGH', 'Thin Dough', 1.0);
INSERT INTO ingredients VALUES (2, 'DOUGH', 'Thick Dough', 1.5);
INSERT INTO ingredients VALUES (3, 'SAUCE', 'Tomato Sauce', 0.5);
INSERT INTO ingredients VALUES (4, 'SAUCE', 'BBQ Sauce', 0.7);
INSERT INTO ingredients VALUES (5, 'TOPPING', 'Cheese', 0.0);
INSERT INTO ingredients VALUES (6, 'TOPPING', 'Salami', 0.5);

INSERT INTO special_offers VALUES (1, 2, 'Thick Dough for the price of thin dough', '');
INSERT INTO special_offers VALUES (2, 5, 'Double cheese gratis', '');
INSERT INTO special_offers VALUES (3, 5, 'Mozzarella for the same price', '');
INSERT INTO special_offers VALUES (4, 6, 'Make it hot!', '');