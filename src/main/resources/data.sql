--Insert Categories

INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'Fruits', 'Fruits');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'Vegetables', 'Vegetables');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'Meat', 'Meat');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'Fish', 'Fish');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'Dairy', 'Dairy');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'Bread', 'Bread');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'Sweets', 'Sweets');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'Drinks', 'Drinks');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'Alcohol', 'Alcohol');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'MOBILES', 'CATEGORY FOR MOBILES');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'LAPTOPS', 'CATEGORY FOR LAPTOPS');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'TV', 'CATEGORY FOR TV');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'CAMERA', 'CATEGORY FOR CAMERA');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'HOME APPLIANCES', 'CATEGORY FOR HOME APPLIANCES');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'BOOKS', 'CATEGORY FOR BOOKS');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'CLOTHES', 'CATEGORY FOR CLOTHES');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'FOOTWEAR', 'CATEGORY FOR FOOTWEAR');
INSERT INTO CATEGORIES(ID, NAME, DESCRIPTION)
VALUES (UUID(), 'WATCHES', 'CATEGORY FOR WATCHES');

-- Insert Products
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Milk', 'Milk', 'Milk', 1.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Drinks'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Apple', 'Apple', 'Apple', 1.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Fruits'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Orange', 'Orange', 'Orange', 2.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Fruits'));

INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Banana', 'Banana', 'Banana', 3.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Fruits'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Potato', 'Potato', 'Potato', 1.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Vegetables'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Tomato', 'Tomato', 'Tomato', 2.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Vegetables'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Cucumber', 'Cucumber', 'Cucumber', 3.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Vegetables'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Pork', 'Pork', 'Pork', 1.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Meat'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Beef', 'Beef', 'Beef', 2.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Meat'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'Chicken', 'Chicken', 'Chicken', 3.99, (SELECT ID FROM CATEGORIES WHERE NAME = 'Meat'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'IPHONE 15', 'IPHONE 15', 'IPHONE 15', 50000, (SELECT ID FROM CATEGORIES WHERE NAME = 'MOBILES'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'IPHONE 16', 'IPHONE 16', 'IPHONE 16', 60000, (SELECT ID FROM CATEGORIES WHERE NAME = 'MOBILES'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'LENOVO', 'LENOVO', 'LENOVO', 50000, (SELECT ID FROM CATEGORIES WHERE NAME = 'LAPTOPS'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'DELL', 'DELL', 'DELL', 60000, (SELECT ID FROM CATEGORIES WHERE NAME = 'LAPTOPS'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'SAMSUNG', 'SAMSUNG', 'SAMSUNG', 50000, (SELECT ID FROM CATEGORIES WHERE NAME = 'TV'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'SONY', 'SONY', 'SONY', 60000, (SELECT ID FROM CATEGORIES WHERE NAME = 'TV'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'CANON', 'CANON', 'CANON', 50000, (SELECT ID FROM CATEGORIES WHERE NAME = 'CAMERA'));
INSERT INTO PRODUCTS(ID, NAME, TITLE, DESCRIPTION, PRICE, CATEGORY_ID)
VALUES (UUID(), 'NIKON', 'NIKON', 'NIKON', 60000, (SELECT ID FROM CATEGORIES WHERE NAME = 'CAMERA'));
