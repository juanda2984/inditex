DROP TABLE IF EXISTS BRAND;

CREATE TABLE BRAND (
  BRAND_ID INT AUTO_INCREMENT  PRIMARY KEY,
  BRAND_NAME VARCHAR(50) NOT NULL
);
 
DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE PRODUCT (
  PRODUCT_ID INT PRIMARY KEY,
  PRODUCT_NAME VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS PRICES;

CREATE TABLE PRICES (
  PRICES_ID INT AUTO_INCREMENT  PRIMARY KEY,
  PRICE NUMERIC(10,2) NOT NULL,
  PRICE_LIST INT NOT NULL,
  PRIORITY INT NOT NULL,
  BRAND_ID INT NOT NULL,
  END_DATE TIMESTAMP NOT NULL,
  PRODUCT_ID INT NOT NULL,
  START_DATE TIMESTAMP NOT NULL,
  CURR VARCHAR(50) NOT NULL,
  foreign key (BRAND_ID) references BRAND(BRAND_ID),
  foreign key (PRODUCT_ID) references PRODUCT(PRODUCT_ID)
);
