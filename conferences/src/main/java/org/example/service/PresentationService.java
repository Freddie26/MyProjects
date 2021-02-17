package org.example.service;

import lombok.AllArgsConstructor;
import org.example.controller.dto.PresentationBrowseOutDto;
import org.example.controller.dto.RegisterPresentationInDto;
import org.example.entity.Presentation;
import org.example.entity.Room;
import org.example.entity.Schedule;
import org.example.entity.User;
import org.example.repository.PresentationRepository;
import org.example.repository.RoomRepository;
import org.example.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PresentationService {

    private final PresentationRepository presentationRepository;

    private final ScheduleRepository scheduleRepository;

    private final RoomRepository roomRepository;

    private final UserService userService;

    public Map<String, PresentationBrowseOutDto> findAllPresentations() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .filter(s -> s.getRoom() != null || s.getRoom().getNumber() != null)
                .collect(Collectors.toMap(s -> s.getRoom().getNumber(), this::toBrowseDto));
    }

    private PresentationBrowseOutDto toBrowseDto(Schedule schedule) {
        String presenters = schedule.getPresentation().getPresenters().stream()
                .map(User::getUsername)
                .collect(Collectors.joining(", "));

        return PresentationBrowseOutDto.builder()
                .title(schedule.getPresentation().getTitle())
                .startDateTime(schedule.getStartDateTime())
                .endDateTime(schedule.getEndDateTime())
                .presenter(presenters)
                .build();
    }

    public boolean registerNewPresentation(RegisterPresentationInDto dto, Long presenterId) {
        LocalDateTime start = dto.getStartDateTime();
        LocalDateTime end = dto.getEndDateTime();

        Optional<Boolean> existsMyOtherPresentation = presentationRepository.checkPresenterOverlaps(presenterId, null, start, end);
        if (existsMyOtherPresentation.isPresent()) {
            return false;
        }

        Optional<Boolean> scheduleOverlaps = presentationRepository.checkScheduleOverlaps(dto.getRoomId(), null, start, end);
        if (scheduleOverlaps.isPresent()) {
            return false;
        }

        Optional<User> optPresenter = userService.findUserById(presenterId);
        if (!optPresenter.isPresent()) {
            return false;
        }

        Optional<Room> optRoom = roomRepository.findById(dto.getRoomId());
        if (!optRoom.isPresent()) {
            return false;
        }

        Presentation presentation = new Presentation();
        presentation.setTitle(dto.getTitle());
        Set<User> presenters = presentation.getPresenters();
        presenters.add(optPresenter.get());
        Presentation savedPresentation = presentationRepository.save(presentation);

        Schedule schedule = new Schedule();
        schedule.setStartDateTime(start);
        schedule.setEndDateTime(end);
        schedule.setPresentation(savedPresentation);
        schedule.setRoom(optRoom.get());
        scheduleRepository.save(schedule);

        return true;
    }

    public boolean updatePresentation(Long presentationId, RegisterPresentationInDto dto, Long presenterId) {
        LocalDateTime start = dto.getStartDateTime();
        LocalDateTime end = dto.getEndDateTime();
        Optional<Schedule> optSchedule = scheduleRepository.findByPresentationId(presentationId);
        if (!optSchedule.isPresent()) {
            return false;
        }

        Schedule schedule = optSchedule.get();
        Presentation presentation = optSchedule.map(Schedule::getPresentation)
                .orElseThrow(() -> new RuntimeException("У расписания отсутствует презентация"));

        Optional<Boolean> existsMyOtherPresentation = presentationRepository.checkPresenterOverlaps(presenterId, presentationId, start, end);
        if (existsMyOtherPresentation.isPresent()) {
            return false;
        }

        Optional<Boolean> scheduleOverlaps = presentationRepository.checkScheduleOverlaps(dto.getRoomId(), presentationId, start, end);
        if (scheduleOverlaps.isPresent()) {
            return false;
        }

        Optional<Room> optRoom = roomRepository.findById(dto.getRoomId());
        if (!optRoom.isPresent()) {
            return false;
        }

        schedule.setStartDateTime(dto.getStartDateTime());
        schedule.setEndDateTime(dto.getEndDateTime());
        schedule.setRoom(optRoom.get());
        scheduleRepository.save(schedule);

        presentation.setTitle(dto.getTitle());
        presentationRepository.save(presentation);

        return true;
    }

    public boolean deletePresentation(Long presentationId) {
        Optional<Presentation> optPresentation = presentationRepository.findById(presentationId);
        if (optPresentation.isPresent()) {
            presentationRepository.deleteById(presentationId);
        }
        return optPresentation.isPresent();
    }

    public boolean registerToPresentation(Long presentationId, Long userId) {
        Optional<Presentation> optPresentation = presentationRepository.findById(presentationId);
        Optional<User> optUser = userService.findUserById(userId);
        optPresentation.ifPresent(presentation -> {
            optUser.ifPresent(user -> {
                Set<User> presentations = presentation.getListeners();
                presentations.add(user);
                presentationRepository.save(presentation);
            });
        });
        return optPresentation.isPresent() && optUser.isPresent();
    }
}
