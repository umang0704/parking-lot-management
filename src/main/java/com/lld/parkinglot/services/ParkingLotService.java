package com.lld.parkinglot.services;

import com.lld.parkinglot.dtos.ParkingLotCreationDto;
import com.lld.parkinglot.dtos.ParkingLotDetailsDto;
import com.lld.parkinglot.enums.*;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.*;
import com.lld.parkinglot.repositories.*;
import java.util.*;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotService {
  @Autowired private ParkingLotRepository parkingLotRepository;
  @Autowired private GateRepository gateRepository;
  @Autowired private ParkingFloorRepository parkingFloorRepository;
  @Autowired private ParkingSpotRepository parkingSpotRepository;
  private Random random = new Random();
  @Autowired private OperatorRepository operatorRepository;

  public ParkingLotDetailsDto createDto(ParkingLotCreationDto parkingLotCreationDto) {
    // create Parking Lot
    ParkingLot parkingLot = parkingLotRepository.save(new ParkingLot());
    // create gates
    createGatesForParkingLot(parkingLotCreationDto, parkingLot);
    // create floors and  parking spots in each floor
    createParkingFloorAndRelatedParkingSpot(parkingLotCreationDto, parkingLot);
    List<Operator> operatorDtoList = new ArrayList<>();
    IntStream.range(0, parkingLotCreationDto.getOperatorCount())
        .forEach(
            operatorIndex -> {
              Operator operator = new Operator();
              operator.setParkingLot(parkingLot);
              operator.setEmployeeId(UUID.randomUUID());
              operator.setName("Operator_" + parkingLot.getId() + operator.getEmployeeId());

              operatorRepository.save(operator);
              operatorDtoList.add(operator);
            });

    ParkingLotDetailsDto parkingLotDetailsDto =
        new ParkingLotDetailsDto(
            parkingLot.getId(),
            (int) parkingFloorRepository.countByParkingLot_Id(parkingLot.getId()),
            (int)
                parkingSpotRepository.countByParkingFloor_IdInAllIgnoreCase(
                    parkingFloorRepository.findIdByParkingLot_Id(parkingLot.getId())),
            operatorDtoList,
            (int)
                gateRepository.getGatesByTypeAndParkingLotId(
                    parkingLot.getId(), GateType.ENTRY.toString()),
            (int)
                gateRepository.getGatesByTypeAndParkingLotId(
                    parkingLot.getId(), GateType.EXIT.toString()));

    return parkingLotDetailsDto;
  }

  private void createParkingFloorAndRelatedParkingSpot(
      ParkingLotCreationDto parkingLotCreationDto, ParkingLot parkingLot) {
    IntStream.range(0, parkingLotCreationDto.getFloors())
        .forEach(
            parkingFloorIndex -> {
              ParkingFloor parkingFloor = new ParkingFloor();
              parkingFloor.setFloor(parkingFloorIndex);
              parkingFloor.setParkingLot(parkingLot);

              parkingFloorRepository.save(parkingFloor);

              IntStream.range(0, parkingLotCreationDto.getSpotsOnFloor())
                  .forEach(
                      parkingSpotOnFloor -> {
                        ParkingSpot parkingSpot = new ParkingSpot();
                        parkingSpot.setParkingFloor(parkingFloor);
                        parkingSpot.setSpotStatus(SpotStatus.EMPTY);
                        parkingSpot.setSupportedVehicleTypes(randomVehicleTypes());
                        parkingSpot.setSpotNumber(parkingSpotOnFloor + 1);
                        parkingSpotRepository.save(parkingSpot);
                      });
            });
  }

  private VehicleType randomVehicleTypes() {
    List<VehicleType> types = Arrays.asList(VehicleType.values());
    Collections.shuffle(Arrays.asList(types));
    return types.get(random.nextInt(types.size()));
  }

  private void createGatesForParkingLot(
      ParkingLotCreationDto parkingLotCreationDto, ParkingLot parkingLot) {
    IntStream.range(0, parkingLotCreationDto.getEntryGates())
        .forEach(
            entryGateIndex -> {
              Gate gate = new Gate();
              gate.setParkingLot(parkingLot);
              gate.setGateStatus(GateStatus.CLOSED);
              gate.setGateType(GateType.ENTRY);
              gate.setGateNumber(entryGateIndex + 1);
              gateRepository.save(gate);
            });
    IntStream.range(0, parkingLotCreationDto.getExitGates())
        .forEach(
            exitGateIndex -> {
              Gate gate = new Gate();
              gate.setParkingLot(parkingLot);
              gate.setGateStatus(GateStatus.CLOSED);
              gate.setGateType(GateType.EXIT);
              gate.setGateNumber(exitGateIndex + 1);
              gateRepository.save(gate);
            });
  }

  public ParkingLot checkIfParkingLotExist(Long parkingLotId) throws ApplicationException {
    Optional<ParkingLot> parkingLot = parkingLotRepository.findById(parkingLotId);
    if (parkingLot.isPresent()) return parkingLot.get();
    else
      throw new ApplicationException(
          ResponseCode.PL_400401, "Parking Lot with id: " + parkingLotId + "  not found!");
  }
}
