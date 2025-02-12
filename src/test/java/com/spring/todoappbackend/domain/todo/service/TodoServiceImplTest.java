package com.spring.todoappbackend.domain.todo.service;

import com.spring.todoappbackend.auth.AuthUtils;
import com.spring.todoappbackend.auth.CustomUserDetails;
import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.todo.dto.TodoRequestDto;
import com.spring.todoappbackend.domain.todo.dto.TodoResponseDto;
import com.spring.todoappbackend.domain.todo.entity.Todo;
import com.spring.todoappbackend.domain.todo.enums.Category;
import com.spring.todoappbackend.domain.todo.repository.TodoRepository;
import com.spring.todoappbackend.domain.user.entity.User;
import com.spring.todoappbackend.domain.user.repository.UserRepository;
import com.spring.todoappbackend.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private TodoServiceImpl todoService;

    private User testUser;
    private Todo testTodo;
    private final Long testUserId = 1L;
    private final Long testTodoId = 1L;

    @BeforeEach
    public void setUp() {
        testUser = User.builder()
                .id(testUserId)
                .uid("testUid")
                .password("testUser")
                .build();

        testTodo = Todo.builder()
                .id(testTodoId)
                .title("Test Todo")
                .category(Category.PERSONAL)
                .completed(false)
                .user(testUser)
                .build();

        UserDetails userDetails = new CustomUserDetails(testTodoId, "testUser");

        // 인증 객체 미리 셋팅
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("모든 Todo 조회 - 성공")
    public void getAllTodoSuccessTest() throws Exception{
        //given
        ReflectionTestUtils.setField(testTodo, "createdAt", LocalDateTime.now());
        when(userService.findUser(testUser.getId())).thenReturn(testUser);
        when(todoRepository.findAllByUser(testUser)).thenReturn(Arrays.asList(testTodo));

        //when
        List<TodoResponseDto> result = todoService.getAllTodos();

        //then
        assertEquals(1, result.size());
        assertEquals(testTodo.getId(), result.get(0).getId());
        assertEquals(testTodo.getTitle(), result.get(0).getTitle());
        assertEquals(testTodo.getCreatedAt().toLocalDate(), result.get(0).getCreatedAt());
        assertFalse(testTodo.isCompleted());
        assertEquals(testTodo.getUser(), testUser);
        assertEquals(testTodo.getCategory(), Category.PERSONAL);
    }
    
    @Test
    @DisplayName("모든 Todo 조회 - 실패")
    public void getAllTodoFailTest() throws Exception{
        //given
        when(userService.findUser(testUser.getId())).thenReturn(testUser);
        when(todoRepository.findAllByUser(testUser)).thenReturn(List.of(testTodo));

        //when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            todoService.getAllTodos();
        });

        //then
        assertEquals("TodoList 불러오기에 실패했습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("Todo 생성 - 성공")
    public void createTodoSuccessTest() throws Exception{
        //given
        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("새로운 Todo")
                .category(Category.WORK)
                .build();

        ReflectionTestUtils.setField(testTodo, "createdAt", LocalDateTime.now());
        when(userService.findUser(testUserId)).thenReturn(testUser);
        when(todoRepository.save(any(Todo.class))).thenReturn(testTodo);
        //when
        TodoResponseDto todoResponseDto = todoService.createTodo(todoRequestDto);

        //then
        assertEquals(testTodoId, todoResponseDto.getId());
        assertEquals("Test Todo", todoResponseDto.getTitle());
        assertEquals(Category.PERSONAL, todoResponseDto.getCategory());
        assertFalse(todoResponseDto.isCompleted());
    }

    @Test
    @DisplayName("Todo 생성 - 실패")
    public void createTodoFailTest() throws Exception{
        //given
        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("새로운 Todo")
                .category(Category.PERSONAL
                )
                .build();

        when(userService.findUser(testUserId)).thenThrow(new RuntimeException("Todo 생성에 실패했습니다."));

        //when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            todoService.createTodo(todoRequestDto);
        });

        //then
        assertEquals("Todo 생성에 실패했습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("Todo 수정 - 성공")
    public void updateTodoSuccessTest() throws Exception{
        //given
        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("Updated HEALTH Title")
                .category(Category.HEALTH)
                .build();

        ReflectionTestUtils.setField(testTodo, "updatedAt", LocalDateTime.now());
        when(todoRepository.save(any(Todo.class))).thenReturn(testTodo);
        when(todoRepository.findById(testTodoId)).thenReturn(Optional.of(testTodo));

        //when
        TodoResponseDto todoResponseDto = todoService.updateTodo(1L, todoRequestDto);

        //then
        verify(todoRepository).save(testTodo);
        assertEquals("Updated HEALTH Title", testTodo.getTitle());
        assertEquals(Category.HEALTH, testTodo.getCategory());
        assertNotNull(todoResponseDto);
        assertFalse(todoResponseDto.isCompleted());
        assertEquals(testTodoId, todoResponseDto.getId());
        assertEquals("Updated HEALTH Title", todoResponseDto.getTitle());
    }

    @Test
    @DisplayName("Todo 수정 - 실패")
    public void updateTodoFailTest() throws Exception{
        //given
        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("Updated HEALTH Title")
                .category(Category.HEALTH)
                .build();

        //when
        when(todoRepository.findById(testTodoId)).thenReturn(Optional.empty());
        //then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            todoService.updateTodo(testTodoId, todoRequestDto);
        });
        assertEquals("Todo 수정에 실패했습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("Todo 완료상태 수정 - 성공")
    public void updateCompleteTodoSuccessTest() throws Exception{
        //given
        when(todoRepository.findById(testTodoId)).thenReturn(Optional.of(testTodo));

        //when
        MessageDto result = todoService.updateTodo(testTodoId);

        //then
        verify(todoRepository).save(testTodo);
        assertTrue(testTodo.isCompleted());
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("수정 완료", result.getMessage());
    }

    @Test
    @DisplayName("Todo 완료상태 수정 - 실패")
    public void updateCompleteTodoFailTest() throws Exception{
        //given
        when(todoRepository.findById(testTodoId)).thenReturn(Optional.empty());
        //when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            todoService.updateTodo(testTodoId);
        });
        //then
        assertEquals("Todo 수정에 실패했습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("Todo 삭제 - 성공")
    public void deleteTodoSuccessTest() throws Exception{
        //given
        when(todoRepository.findById(testTodoId)).thenReturn(Optional.of(testTodo));

        //when
        MessageDto result = todoService.deleteTodo(testTodoId);

        //then
        verify(todoRepository).delete(testTodo);
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("삭제 완료.", result.getMessage());
    }

    @Test
    @DisplayName("Todo 삭제 - 실패")
    public void deleteTodoFailTest() throws Exception{
        //when
        when(userService.findUser(testUserId)).thenReturn(testUser);
        when(todoRepository.findById(testTodoId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            todoService.deleteTodo(testTodoId);
        });
        //then
        assertEquals("Todo 삭제에 실패했습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("Todo 검색 - 성공")
    public void searchTodosSuccessTest() throws Exception{
        //given
        String keyword = "Test";

        ReflectionTestUtils.setField(testTodo, "createdAt", LocalDateTime.now());
        when(userService.findUser(testUserId)).thenReturn(testUser);
        when(todoRepository.findByTitleContainsAndUser(keyword, testUser)).thenReturn(List.of(testTodo));
        //when

        List<TodoResponseDto> todos = todoService.searchTodos(keyword);

        //then
        assertNotNull(todos);
        assertEquals(1, todos.size());
        assertTrue(todos.get(0).getTitle().contains(keyword));
    }
}