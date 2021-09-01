package ru.dromran.testtz.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.dromran.testtz.dto.AssignmentFormDTO;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.entity.AssignmentEntity;
import ru.dromran.testtz.service.AssignmentService;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static ru.dromran.testtz.constants.RoleConstants.ADMIN_ROLE;
import static ru.dromran.testtz.constants.RoleConstants.USER_ROLE;

@RestController
@RequestMapping("/api/assignment")
@Secured({USER_ROLE, ADMIN_ROLE})
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @PostMapping("")
    public ResponseEntity<AssignmentEntity> createAssignment(@RequestBody @Valid AssignmentFormDTO formDTO) {
        return ResponseEntity.ok(assignmentService.createAssignment(formDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentEntity> getAssignmentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(assignmentService.getByIdAssignment(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<AssignmentEntity>> findAssignments(@RequestParam(required = false) Integer pageSize,
                                                                  @RequestParam(required = false) Integer pageSkip,
                                                                  @RequestParam(required = false) String textTerm,
                                                                  @RequestParam(required = false) Long executorId,
                                                                  @RequestParam(required = false)
                                                                  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                                                                  @JsonSerialize(using = LocalDateTimeSerializer.class)
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                          LocalDateTime fromDeadLine,
                                                                  @RequestParam(required = false)
                                                                  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                                                                  @JsonSerialize(using = LocalDateTimeSerializer.class)
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                          LocalDateTime toDeadline,
                                                                  @RequestParam(required = false) String typeTerm,
                                                                  @RequestParam(required = false) Long authorId) {
        return ResponseEntity.ok(assignmentService.findAssignment(pageSize,
                pageSkip, textTerm, executorId, fromDeadLine, toDeadline, typeTerm, authorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentEntity> updateAssignment(@PathVariable("id") Long id,
                                                             @RequestBody @Valid AssignmentFormDTO assignmentFormDTO) {
        return ResponseEntity.ok(assignmentService.updateAssignment(id, assignmentFormDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteAssignment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(assignmentService.deleteAssignment(id));
    }

    @PutMapping("/{id}/work")
    public ResponseEntity<AssignmentEntity> toWorkAssignment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(assignmentService.moveToWorkAssignment(id));
    }

    @PutMapping("/{id}/check")
    public ResponseEntity<AssignmentEntity> executorCheckWork(@PathVariable("id") Long id,
                                                              @RequestParam Boolean checkState) {
        return ResponseEntity.ok(assignmentService.setExecutorCheckWork(id, checkState));
    }

    @PutMapping("/{id}/rework")
    public ResponseEntity<AssignmentEntity> toReWork(@PathVariable("id") Long id) {
        return ResponseEntity.ok(assignmentService.moveToReWorkAssignment(id));
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<AssignmentEntity> doneAssignment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(assignmentService.moveToDoneAssignment(id));
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<AssignmentEntity> assignUserToTask(@PathVariable("id") Long id) {
        return ResponseEntity.ok(assignmentService.assignUserToTask(id));
    }

}
