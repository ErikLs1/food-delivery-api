# Food Delivery API
Tha application calculates the delivery fee based on vehicle and city fee, as well as weather conditions.
It includes CRUD endpoints for managing business related logic, scheduled task that reads weather data from Estonian 
weather website and a single endpoint that returns the total delivery based on cit, vehicle and weather condition.

## Data Model
Below is the picture of the ERD Model for this application.
![image alt](img.png)

## Business Rules
Below you can take a closer look at how the extra fee will be calculated based on the weather condition.

| Condition Type | Vehicle (s)   | Rules                                 | Extra Fee | UsageForbidden |
|----------------|---------------|---------------------------------------|-----------|----------------|
| Temperature    | Scooter, Bike | Air temp < -10 °C                     | + 1.00    | False          |          
| Temperature    | Scooter, Bike | -10°C ≤ Air temp ≤ 0°C                | + 0.50    | False          |          
| Wind           | Bike          | -10 m/s ≤ Air temp ≤ 20 m/s           | + 0.50    | False          |          
| Wind           | Bike          | Wind > 20 m/s                         | + 0.00    | True           |          
| Phenomenon     | Scooter, Bike | Snow / Sleet (e.g. Heavy snow shower) | + 1.00    | False          |          
| Phenomenon     | Scooter, Bike | Rain (e.g. Light Rain)                | + 0.50    | False          |          
| Phenomenon     | Scooter, Bike | Glaze / Hail/ Thunder                 | + 0.00    | True           |          

Below is the fee based on city and vehicle.

| City    | CAR   | SCOOTER | Extra Fee | 
|---------|-------|---------|-----------|
| Tallinn | 4.00€ | 3.50€   | 3.00€     |          
| Tartu   | 3.50€ | 3.00€   | 2.50€     |         
| Pärnu   | 3.00€ | 2.50€   | 2.00€     |        


## Delivery Fee Endpoint
### 1. **DeliveryFeeController**
**Endpoint**: `/api/delivery-fee`  
**Method**: `GET`  
**Request Body**: `DeliveryFeeRequestDTO`  
```json
{
    "cityName": "Tallinn",
    "vehicleType": "Scooter",
    "observationTime": "2025-03-12T13:34:32 (Optional)"
}
```

**Response Body**: `DeliveryResponseDTO`
```json
{
    "totalFee": 3.5
}
```

## Business Rules Controllers
### 1. **BaseFeeControllers**
#### Create a Base Fee
**Endpoint**: `/api/base-fee`  
**Method**: `POST`  
**Request Body**: `BaseFeeDTO`

```json
{
    "cityId": 1,
    "vehicleId": 2,
    "vehicleFee": 3.50
}
```
---
#### Update a Base Fee
**Endpoint**: `/api/base-fee/{id}`  
**Method**: `PUT`  
**Request Body**: `BaseFeeDTO`

```json
{
    "cityId": 1,
    "vehicleId": 2,
    "vehicleFee": 5.00
}
```
---
#### Get Base Fee by ID
**Endpoint**: `/api/base-fee/{id}`  
**Method**: `GET`  
**Response**: `BaseFeeDTO`
---
#### Get All Base Fees
**Endpoint**: `/api/base-fee`  
**Method**: `GET`  
**Response**: List of `BaseFeeDTO`
---
#### Delete Base Fee by ID
**Endpoint**: `/api/base-fee/{id}`  
**Method**: `DELETE`  
---
### 2. **CityControllers**
#### Create a City
**Endpoint**: `/api/city`  
**Method**: `POST`  
**Request Body**: `CityDTO`

```json
{
    "cityId": 1,
    "vehicleId": 2,
    "vehicleFee": 3.50
}
```
---
#### Update a City
**Endpoint**: `/api/city/{id}`  
**Method**: `PUT`  
**Request Body**: `CityDTO`

```json
{
    "cityId": 1,
    "vehicleId": 2,
    "vehicleFee": 5.00
}
```
---
#### Get City by ID
**Endpoint**: `/api/city/{id}`  
**Method**: `GET`  
**Response**: `CityDTO`
---
#### Get All Cities
**Endpoint**: `/api/city`  
**Method**: `GET`  
**Response**: List of `CityDTO`
---
#### Delete City by ID
**Endpoint**: `/api/city/{id}`  
**Method**: `DELETE`  
---
### 3. **ConditionsControllers**
#### Create a Condition
**Endpoint**: `/api/conditions`  
**Method**: `POST`  
**Request Body**: `ConditionsDTO`

```json
{
    "vehicleId": 1,
    "conditionType": "TEMPERATURE",
    "minValue": -10.00,
    "maxValue": 0.00,
    "phenomenon": "OPTIONAL",
    "conditionFee": 1.00,
    "usageForbidden": false
}
```
**or**
```json
{
  "vehicleId": 1,
  "conditionType": "PHENOMENON",
  "minValue": "OPTIONAL",
  "maxValue": "OPTIONAL",
  "phenomenon": "Light snow shower",
  "conditionFee": 1.00,
  "usageForbidden": false
}
```
---
#### Update a Condition
**Endpoint**: `/api/conditions/{id}`  
**Method**: `PUT`  
**Request Body**: `ConditionsDTO`

```json
{
  "vehicleId": 1,
  "conditionType": "TEMPERATURE",
  "minValue": -99.00,
  "maxValue": -10.01,
  "phenomenon": "OPTIONAL",
  "conditionFee": 0.00,
  "usageForbidden": true
}
```
---
#### Get Condition by ID
**Endpoint**: `/api/conditions/{id}`  
**Method**: `GET`  
**Response**: `ConditionsDTO`
---
#### Get All Conditions
**Endpoint**: `/api/conditions`  
**Method**: `GET`  
**Response**: List of `ConditionsDTO`
---
#### Delete Conditions by ID
**Endpoint**: `/api/conditions/{id}`  
**Method**: `DELETE`
---
### 4. **VehicleControllers**
#### Create a Vehicle
**Endpoint**: `/api/vehicle`  
**Method**: `POST`  
**Request Body**: `VehicleDTO`

```json
{
  "vehicleType": "SCOOTER"
}
```
---
#### Update a Vehicle
**Endpoint**: `/api/vehicle/{id}`  
**Method**: `PUT`  
**Request Body**: `VehicleDTO`

```json
{
  "vehicleType": "CAR"
}
```
---
#### Get Vehicle by ID
**Endpoint**: `/api/vehicle/{id}`  
**Method**: `GET`  
**Response**: `VehicleDTO`
---
#### Get All Vehicles
**Endpoint**: `/api/vehicle`  
**Method**: `GET`  
**Response**: List of `VehicleDTO`
---
#### Delete Vehicle by ID
**Endpoint**: `/api/vehicle/{id}`  
**Method**: `DELETE`
---










