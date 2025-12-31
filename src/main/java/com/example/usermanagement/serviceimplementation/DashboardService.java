package com.example.usermanagement.serviceimplementation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.DashboardResponseDTO;
import com.example.usermanagement.dto.MonthlyRegistrationDTO;
import com.example.usermanagement.dto.WeeklyActivityDTO;
import com.example.usermanagement.entity.Role;
import com.example.usermanagement.repository.UserRepository;

@Service
@Lazy
public class DashboardService {

    @Autowired
    private UserRepository userRepo;

    public DashboardResponseDTO getDashboardStats() {

        LocalDateTime startOfMonth =
            LocalDate.now().withDayOfMonth(1).atStartOfDay();

        LocalDateTime startOfToday =
            LocalDate.now().atStartOfDay();

        return new DashboardResponseDTO(
            userRepo.count(),
            userRepo.countByCreatedAtAfter(startOfMonth),
            userRepo.countByRole(Role.ADMIN),
            userRepo.countByLastLoginAtAfter(startOfToday)
        );
    }
    public List<MonthlyRegistrationDTO> getMonthlyRegistrations() {

        int year = LocalDate.now().getYear();

        List<Object[]> results = userRepo.getMonthlyRegistrations(year);

        Map<Integer, Long> monthMap = new HashMap<>();

        for (Object[] row : results) {
            Integer month = (Integer) row[0];
            Long count = (Long) row[1];
            monthMap.put(month, count);
        }

        List<MonthlyRegistrationDTO> response = new ArrayList<>();

        for (int m = 1; m <= 12; m++) {
            String monthName = Month.of(m)
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            response.add(
                new MonthlyRegistrationDTO(
                    monthName,
                    monthMap.getOrDefault(m, 0L)
                )
            );
        }

        return response;
    }
    
    public List<WeeklyActivityDTO> getWeeklyActivity() {

        LocalDateTime startDate = LocalDate.now()
                .minusDays(6)
                .atStartOfDay();

        Map<Integer, Long> loginMap = new HashMap<>();
        Map<Integer, Long> signupMap = new HashMap<>();

        // DB: 1 = SUN ... 7 = SAT
        userRepo.getWeeklyLogins(startDate)
                .forEach(r -> loginMap.put((Integer) r[0], (Long) r[1]));

        userRepo.getWeeklySignups(startDate)
                .forEach(r -> signupMap.put((Integer) r[0], (Long) r[1]));

        List<WeeklyActivityDTO> response = new ArrayList<>();

        // Explicit order: SUN → SAT
        DayOfWeek[] orderedDays = {
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
        };

        for (int i = 0; i < 7; i++) {
            int dbDayIndex = i + 1; // DB uses 1–7 (SUN–SAT)
            DayOfWeek day = orderedDays[i];

            response.add(
                    new WeeklyActivityDTO(
                            day.name().substring(0, 3),
                            loginMap.getOrDefault(dbDayIndex, 0L),
                            signupMap.getOrDefault(dbDayIndex, 0L)
                    )
            );
        }

        return response;
    }


}

