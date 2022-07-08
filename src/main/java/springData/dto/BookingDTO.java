package springData.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
@Component
public class BookingDTO {
    private String userName;
    private String status;
    private Date bookingDate;
    private List<FlightDTO> flightList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public List<FlightDTO> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<FlightDTO> flightList) {
        this.flightList = flightList;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "userName='" + userName + '\'' +
                ", status='" + status + '\'' +
                ", bookingDate=" + bookingDate +
                ", flightList=" + flightList +
                '}';
    }
}
