package springData.repository;

import springData.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    void deleteFlightById(Integer id);
    @Override
    void deleteAll();
    Flight findFlightByFlightNumber(Integer flightNumber);
    @Query(value = "SELECT booking_id FROM new_db.booking_flight WHERE flight_id = :id", nativeQuery = true)
    List<Integer> findAllById(@Param("id") Integer id);
    @Query(value = "select user_id from booking where id IN(select booking_id from booking_flight where flight_id = :flightId)", nativeQuery = true)
    public List<Integer> findAllById(@Param("flightId") int id);
}
