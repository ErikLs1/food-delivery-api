DROP TABLE IF EXISTS BASE_FEE;
DROP TABLE IF EXISTS WEATHER_DATA;
DROP TABLE IF EXISTS CONDITIONS;
DROP TABLE IF EXISTS VEHICLE;
DROP TABLE IF EXISTS CITY;

CREATE TABLE CITY (
    city_id INT AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(100) NOT NULL,
    station_name VARCHAR(100) NOT NULL,
    wmo_code VARCHAR(50)
);

CREATE TABLE VEHICLE (
    vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_type VARCHAR(50) NOT NULL
);

CREATE TABLE WEATHER_DATA (
    weather_data_id INT AUTO_INCREMENT PRIMARY KEY,
    city_id INT NOT NULL,
    air_temperature DECIMAL(5, 2),
    wind_speed DECIMAL(5, 2),
    weather_phenomenon VARCHAR(100),
    observation_time TIMESTAMP NOT NULL,
    FOREIGN KEY (city_id) REFERENCES CITY(city_id)
);

CREATE TABLE BASE_FEE (
    base_fee_id INT AUTO_INCREMENT PRIMARY KEY,
    city_id INT NOT NULL,
    vehicle_id INT NOT NULL,
    vehicle_fee DECIMAL(5, 2),
    FOREIGN KEY (city_id) REFERENCES CITY(city_id),
    FOREIGN KEY (vehicle_id) REFERENCES VEHICLE(vehicle_id)
);

CREATE TABLE CONDITIONS (
    conditions_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id INT NOT NULL,
    condition_type VARCHAR(50) NOT NULL,
    min_value DECIMAL(5,2),
    max_value DECIMAL(5,2),
    phenomenon VARCHAR(100),
    condition_fee DECIMAL(5, 2) DEFAULT 0.00,
    usage_forbidden BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (vehicle_id) REFERENCES VEHICLE(vehicle_id)
);

INSERT INTO CITY (city_name, station_name, wmo_code) VALUES ('Tallinn', 'Tallinn-Harku', '26038');
INSERT INTO CITY (city_name, station_name, wmo_code) VALUES ('Tartu', 'Tartu-Tõravere', '26242');
INSERT INTO CITY (city_name, station_name, wmo_code) VALUES ('Pärnu', 'Pärnu', '41803');

INSERT INTO VEHICLE (vehicle_type) VALUES ('CAR');
INSERT INTO VEHICLE (vehicle_type) VALUES ('SCOOTER');
INSERT INTO VEHICLE (vehicle_type) VALUES ('BIKE');

INSERT INTO BASE_FEE (city_id, vehicle_id, vehicle_fee)
SELECT c.city_id, v.vehicle_id, 4.00
FROM CITY c, VEHICLE v
WHERE c.city_name = 'Tallinn' AND v.vehicle_type = 'CAR';

INSERT INTO BASE_FEE (city_id, vehicle_id, vehicle_fee)
SELECT c.city_id, v.vehicle_id, 3.50
FROM CITY c, VEHICLE v
WHERE c.city_name = 'Tallinn' AND v.vehicle_type = 'SCOOTER';

INSERT INTO BASE_FEE (city_id, vehicle_id, vehicle_fee)
SELECT c.city_id, v.vehicle_id, 3.00
FROM CITY c, VEHICLE v
WHERE c.city_name = 'Tallinn' AND v.vehicle_type = 'BIKE';

INSERT INTO BASE_FEE (city_id, vehicle_id, vehicle_fee)
SELECT c.city_id, v.vehicle_id, 3.50
FROM CITY c, VEHICLE v
WHERE c.city_name = 'Tartu' AND v.vehicle_type = 'CAR';

INSERT INTO BASE_FEE (city_id, vehicle_id, vehicle_fee)
SELECT c.city_id, v.vehicle_id, 3.00
FROM CITY c, VEHICLE v
WHERE c.city_name = 'Tartu' AND v.vehicle_type = 'SCOOTER';

INSERT INTO BASE_FEE (city_id, vehicle_id, vehicle_fee)
SELECT c.city_id, v.vehicle_id, 2.50
FROM CITY c, VEHICLE v
WHERE c.city_name = 'Tartu' AND v.vehicle_type = 'BIKE';

INSERT INTO BASE_FEE (city_id, vehicle_id, vehicle_fee)
SELECT c.city_id, v.vehicle_id, 3.00
FROM CITY c, VEHICLE v
WHERE c.city_name = 'Pärnu' AND v.vehicle_type = 'CAR';

INSERT INTO BASE_FEE (city_id, vehicle_id, vehicle_fee)
SELECT c.city_id, v.vehicle_id, 2.50
FROM CITY c, VEHICLE v
WHERE c.city_name = 'Pärnu' AND v.vehicle_type = 'SCOOTER';

INSERT INTO BASE_FEE (city_id, vehicle_id, vehicle_fee)
SELECT c.city_id, v.vehicle_id, 2.00
FROM CITY c, VEHICLE v
WHERE c.city_name = 'Pärnu' AND v.vehicle_type = 'BIKE';

-- snow/fleet related condition for scooter
INSERT INTO CONDITIONS(vehicle_id, condition_type, phenomenon, condition_fee, usage_forbidden)
VALUES
    (2, 'PHENOMENON', 'Light snow shower', 1.00, FALSE),
    (2, 'PHENOMENON', 'Moderate snow shower', 1.00, FALSE),
    (2, 'PHENOMENON', 'Heavy snow shower', 1.00, FALSE),
    (2, 'PHENOMENON', 'Light sleet', 1.00, FALSE),
    (2, 'PHENOMENON', 'Moderate sleet', 1.00, FALSE),
    (2, 'PHENOMENON', 'Light snowfall', 1.00, FALSE),
    (2, 'PHENOMENON', 'Moderate snowfall', 1.00, FALSE),
    (2, 'PHENOMENON', 'Heavy snowfall', 1.00, FALSE);

-- snow/fleet related conditions for bike
INSERT INTO CONDITIONS(vehicle_id, condition_type, phenomenon, condition_fee, usage_forbidden)
VALUES
    (3, 'PHENOMENON', 'Light snow shower', 1.00, FALSE),
    (3, 'PHENOMENON', 'Moderate snow shower', 1.00, FALSE),
    (3, 'PHENOMENON', 'Heavy snow shower', 1.00, FALSE),
    (3, 'PHENOMENON', 'Light sleet', 1.00, FALSE),
    (3, 'PHENOMENON', 'Moderate sleet', 1.00, FALSE),
    (3, 'PHENOMENON', 'Light snowfall', 1.00, FALSE),
    (3, 'PHENOMENON', 'Moderate snowfall', 1.00, FALSE),
    (3, 'PHENOMENON', 'Heavy snowfall', 1.00, FALSE);

-- rain related condition for scooter
INSERT INTO CONDITIONS(vehicle_id, condition_type, phenomenon, condition_fee, usage_forbidden)
VALUES
    (2, 'PHENOMENON', 'Light shower', 0.50, FALSE),
    (2, 'PHENOMENON', 'Moderate shower', 0.50, FALSE),
    (2, 'PHENOMENON', 'Heavy shower', 0.50, FALSE),
    (2, 'PHENOMENON', 'Light rain', 0.50, FALSE),
    (2, 'PHENOMENON', 'Moderate rain', 0.50, FALSE),
    (2, 'PHENOMENON', 'Heavy rain', 0.50, FALSE);

-- rain related condition for bike
INSERT INTO CONDITIONS(vehicle_id, condition_type, phenomenon, condition_fee, usage_forbidden)
VALUES
    (3, 'PHENOMENON', 'Light shower', 0.50, FALSE),
    (3, 'PHENOMENON', 'Moderate shower', 0.50, FALSE),
    (3, 'PHENOMENON', 'Heavy shower', 0.50, FALSE),
    (3, 'PHENOMENON', 'Light rain', 0.50, FALSE),
    (3, 'PHENOMENON', 'Moderate rain', 0.50, FALSE),
    (3, 'PHENOMENON', 'Heavy rain', 0.50, FALSE);

-- glaze, hail, thunder --> scooter usage forbidden.
INSERT INTO CONDITIONS(vehicle_id, condition_type, phenomenon, condition_fee, usage_forbidden)
VALUES
    (2, 'PHENOMENON', 'Glaze', 0.00, TRUE),
    (2, 'PHENOMENON', 'Hail', 0.00, TRUE),
    (2, 'PHENOMENON', 'Thunder', 0.00, TRUE);


-- glaze, hail, thunder --> bike usage forbidden.
INSERT INTO CONDITIONS(vehicle_id, condition_type, phenomenon, condition_fee, usage_forbidden)
VALUES
    (3, 'PHENOMENON', 'Glaze', 0.00, TRUE),
    (3, 'PHENOMENON', 'Hail', 0.00, TRUE),
    (3, 'PHENOMENON', 'Thunder', 0.00, TRUE);

-- Temperature rules for scooter and bike.
INSERT INTO CONDITIONS(vehicle_id, condition_type, min_value, max_value, condition_fee, usage_forbidden)
VALUES
    (2, 'TEMPERATURE', -99.99, -10.01, 1.00, FALSE),
    (3, 'TEMPERATURE', -99.99, -10.01,  1.00, FALSE);

INSERT INTO CONDITIONS(vehicle_id, condition_type, min_value, max_value, condition_fee, usage_forbidden)
VALUES
    (2, 'TEMPERATURE', -10.00, 0.00,0.50, FALSE),
    (3, 'TEMPERATURE', -10.00, 0.00,0.50, FALSE);

 -- Wind rules for bike
INSERT INTO CONDITIONS(vehicle_id, condition_type, min_value, max_value, condition_fee, usage_forbidden)
VALUES
    (3, 'WIND', 10, 20, 0.50, FALSE),
    (3, 'WIND', 20.01, 99.99, 0.50, TRUE);

