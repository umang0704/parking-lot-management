# Parking Log Management System

## Requirements

```markdown
1. Parking Lot has entry and exit gates. 
2. Parking Lot can have multiple floors. 
3. Each Floor can have multiple Parking spots.
4. Each spot can support different kind of vehicles
5. Customer while entering the lot will get a ticket by operator having the parking spot details and IN time.
6. While exiting bill will be calculated on the basis of exit time.
7. Different Payment modes are excepted.
```

## Entity
![parking-lot-class.svg](src%2Fmain%2Fresources%2Fstatic%2Fparking-lot-class.svg)
## Flows

### Ticket Creation:
```
Input : ParkingLotId, OperatorId, VehicleNumber, VehicleType, GateId
1. Validations
    - parking lot should exist
    - gate should be mapped with parking lot
    - operator should be mapped with parking lot
    - gate should be open
2. Fetch or Create Vehicle Object
3. Check if any open ticket for the Vehicle exists
4. Find a empty spot (*)
    - vehicleType present: use SpotSelectionByVehicleTypeStategy
    - vehicleType not present : use SpotSelectionByAllVehicle=Stategy
5. Create Ticket
6. Mark spot occupied

(*) Here we need to make sure, no two vehicles are assigned to one spot.
    - We will have a lock on method which fetched the spot
    - In the same method we will change the status of that spot from EMPTY to BLOCKED
```

### Bill Generation:
```
Input : vehicle number, operatorId, gateNumber
1. Validations
    - verify vehicle
    - verify operator
    - verify gateNumber
2. Calculate Bill sing Bill Calculation Strategy
3. Return Bill
```
