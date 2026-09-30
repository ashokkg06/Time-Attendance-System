package com.wipro.service;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.entity.Attendance;
import com.wipro.entity.ClockInOut;
import com.wipro.entity.User;
import com.wipro.repository.AttendanceRepository;
import com.wipro.repository.UserRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
    private final LocalTime standardWorkTime = LocalTime.of(9, 0); // Standard 9 hours

    public List<Attendance> getAttendanceRecordsByUser(Long userId) {
        return attendanceRepository.findByEmployeeId(userId);
    }

    public Attendance getAttendanceOfUserByDate(Long userId, LocalDate date) {
    	return attendanceRepository.findByEmployeeIdAndLoginDate(userId, date);
    }
    
    public Attendance createDailyAttendance(Long userId) {
    	User employee = userRepository.findById(userId).get();
    	employee.setCheckedIn(true);
    	
    	Attendance attendance = new Attendance();

    	attendance.setEmployee(employee);
    	attendance.setLoginDate(LocalDate.now());
    	attendance.setFirstIn(LocalTime.parse(LocalTime.now().format(formatter1)));
        
    	attendance.setClockInOuts(new LinkedList<>());
        attendance.getClockInOuts().add(addClock(attendance));
        
        return attendanceRepository.save(attendance);
    }
    
    public Attendance UpdateLogOutAttendance(Long attendanceId) {
    	Attendance attendance = attendanceRepository.findById(attendanceId).get();
    	
    	List<ClockInOut> clocks = attendance.getClockInOuts();
    	ClockInOut clock = clocks.get(clocks.size() - 1);
    	
        LocalTime now = LocalTime.now();
        attendance.setLastOut(LocalTime.parse(now.format(formatter1)));
        clock.setClockOut(LocalTime.parse(now.format(formatter1)));
    	
    	LocalTime clockIn = clock.getClockIn();
        LocalTime clockOut = clock.getClockOut();
        
        LocalTime totalHours = LocalTime.MIN.plus(Duration.between(clockIn, clockOut));
        clock.setTotalHours(totalHours);
        
        if(attendance.getTotalHours()!=null) {
        	LocalTime finalTotalHours = attendance.getTotalHours()
        			.plusHours(totalHours.getHour())
        			.plusMinutes(totalHours.getMinute())
        			.plusSeconds(totalHours.getSecond());
        	attendance.setTotalHours(finalTotalHours);
        }
        else
        	attendance.setTotalHours(totalHours);
        
        boolean isLessThanNineHours = attendance.getTotalHours().isBefore(standardWorkTime);

        // Net Hours calculation (totalHours - 9 hours)
        Duration netDuration = isLessThanNineHours
            ? Duration.between(attendance.getTotalHours(), standardWorkTime)
            : Duration.between(standardWorkTime, attendance.getTotalHours());

        LocalTime netHours = LocalTime.parse(LocalTime.MIN.plus(netDuration).format(formatter2));

        // Setting net hours (negative if totalHours < 9 hours)
        attendance.setNetHours(isLessThanNineHours ? ("- " + netHours.toString()) : ("+ " + netHours.toString()));

        // Overtime calculation (only positive if totalHours > 9 hours)
        if (!isLessThanNineHours) {
            double overtimeHours = netDuration.toMinutes() / 60.0; // Converting here minutes to hours
            DecimalFormat df = new DecimalFormat("#.##");
    		overtimeHours = Double.valueOf(df.format(overtimeHours));
            attendance.setOvertimeHours(overtimeHours);
        }
        
        attendance.getEmployee().setCheckedIn(false);
        return attendanceRepository.save(attendance);
    }
    
    public Attendance UpdateLogInAttendance(Long attendanceId) {
    	Attendance attendance = attendanceRepository.findById(attendanceId).get();
    	
    	if(!attendance.getLoginDate().equals(LocalDate.now()))
    		this.createDailyAttendance(attendance.getEmployee().getId());
    	else {
	        attendance.getEmployee().setCheckedIn(true);
	        attendance.getClockInOuts().add(addClock(attendance));
    	}
        return attendanceRepository.save(attendance);
    }
    
    public ClockInOut addClock(Attendance attendance) {
        return new ClockInOut(LocalDate.now(), LocalTime.parse(LocalTime.now().format(formatter1)), attendance);
    }
}