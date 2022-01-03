package org.example.service;

import org.assertj.core.api.Assertions;
import org.example.entity.Presentation;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.PresentationRepository;
import org.example.repository.RoomRepository;
import org.example.repository.ScheduleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresentationServiceTest {

    @Mock
    private PresentationRepository presentationRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UserService userService;

    private PresentationService presentationService;

    @Test
    public void registerToPresentation() {
        final Long userId = 1L;
        final Long presentationId = 2L;

        Presentation mockPresentation = new Presentation();
        mockPresentation.setId(presentationId);

        when(presentationRepository.findById(presentationId)).thenReturn(Optional.of(mockPresentation));

        Role mockListenerRole = new Role();
        mockListenerRole.setId(1L);
        mockListenerRole.setName("LISTENER");

        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setRole(mockListenerRole);

        when(userService.findUserById(userId)).thenReturn(Optional.of(mockUser));

        PresentationService presentationService = new PresentationService(presentationRepository, scheduleRepository, roomRepository, userService);
        boolean successful = presentationService.registerToPresentation(presentationId, userId);

        Assertions.assertThat(successful).isTrue();
    }
}