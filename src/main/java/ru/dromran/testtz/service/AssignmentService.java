package ru.dromran.testtz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dromran.testtz.dto.AssignmentFormDTO;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.entity.AssignmentEntity;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.entity.ExecutorAssignmentEntity;
import ru.dromran.testtz.entity.OrganizationEntity;
import ru.dromran.testtz.exception.BadRequestException;
import ru.dromran.testtz.exception.ForbiddenRequestException;
import ru.dromran.testtz.exception.NotFoundException;
import ru.dromran.testtz.repository.AssignmentEntityRepository;
import ru.dromran.testtz.repository.EmployeeEntityRepository;
import ru.dromran.testtz.repository.ExecutorAssignmentEntityRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.dromran.testtz.constants.RoleConstants.ADMIN_ROLE;

@Service
@Slf4j
public class AssignmentService {

    @Autowired
    AssignmentEntityRepository assignmentEntityRepository;
    @Autowired
    EmployeeEntityRepository employeeEntityRepository;
    @Autowired
    ExecutorAssignmentEntityRepository executorAssignmentEntityRepository;
    @Autowired
    AuthService authService;

    private Set<EmployeeEntity> getExecutorsEntity(List<Long> ids,
                                                   EmployeeEntity userBySession,
                                                   OrganizationEntity organizationBySession) {
        Set<EmployeeEntity> employeeEntitySet = new HashSet<>();
        if (ids != null) {
            ids.forEach(executorId -> {
                EmployeeEntity employee = employeeEntityRepository.findById(executorId).orElse(null);
                if (employee == null) {
                    NotFoundException notFoundException = new NotFoundException("User executor not found");
                    log.error("Cant found executor", notFoundException);
                    throw notFoundException;
                } else if (!employee.getDepartment().getOrganization().getId().equals(organizationBySession.getId()) &&
                        !userBySession.getRole().equals(ADMIN_ROLE)) {
                    ForbiddenRequestException forbiddenRequestException =
                            new ForbiddenRequestException("You dont have enough permissions to assign this task to " +
                                    "user from another organization");
                    log.error("Try assign task to user from another organiation admin/chef account",
                            forbiddenRequestException);
                    throw forbiddenRequestException;
                } else {
                    employeeEntitySet.add(employee);
                }
            });
        }
        return employeeEntitySet;
    }

    @Transactional
    public AssignmentEntity createAssignment(AssignmentFormDTO formDTO) {
        EmployeeEntity userBySession = authService.getUserBySession();
        OrganizationEntity organizationBySession = userBySession.getDepartment().getOrganization();

        if (formDTO.getDeadLine().isBefore(LocalDateTime.now())) {
            BadRequestException badRequestException = new BadRequestException("Deadline must be in future");
            log.error("Try create assignment with past deadline", badRequestException);
            throw badRequestException;
        }

        Set<EmployeeEntity> employeeEntitySet = getExecutorsEntity(formDTO.getExecutors(),
                userBySession, organizationBySession);

        AssignmentEntity assignment = new AssignmentEntity();
        assignment.setAssignmentText(formDTO.getAbout());
        assignment.setAssignmentDeadLine(formDTO.getDeadLine());
        assignment.setAuthorId(userBySession.getId());
        assignment.setType(formDTO.getType());
        assignment.setIsExecution(false);
        assignment.setIsControl(false);
        AssignmentEntity save = assignmentEntityRepository.save(assignment);
        employeeEntitySet.forEach(executor -> {
            ExecutorAssignmentEntity eae = new ExecutorAssignmentEntity();
            eae.setExecutorAssignmentId(save.getId());
            eae.setExecutorUserId(executor.getId());
            executorAssignmentEntityRepository.save(eae);
        });
        return assignmentEntityRepository.getById(save.getId());
    }

    public AssignmentEntity getByIdAssignment(Long id) {
        AssignmentEntity assignment = assignmentEntityRepository.findById(id).orElse(null);
        if (assignment == null) {
            NotFoundException notFoundException = new NotFoundException("Assignment not found");
            log.error("Cant found Assignment", notFoundException);
            throw notFoundException;
        }
        return assignment;
    }

    private boolean compareTerms(String string, String subString) {
        String toCheck = subString == null ? "" : subString.toLowerCase(Locale.ROOT);
        return string.toLowerCase(Locale.ROOT).contains(toCheck) || toCheck.contains(string.toLowerCase(Locale.ROOT));
    }

    public Page<AssignmentEntity> findAssignment(Integer pageSize,
                                                 Integer pageSkip,
                                                 String textTerm,
                                                 Long executorId,
                                                 LocalDateTime fromDeadLine,
                                                 LocalDateTime toDeadline,
                                                 String typeTerm,
                                                 Long authorId) {
        int realPage = pageSkip == null ? 0 : pageSkip;
        int realSize = pageSize == null ? 10 : pageSize;
        Pageable pageable = PageRequest.of(realPage, realSize);
        //овнокод: ничего лучше, чем это я не придумал
        List<AssignmentEntity> all = assignmentEntityRepository.findAll();
        List<AssignmentEntity> filtered = all.parallelStream().filter(assignmentEntity -> {
            boolean textTermContains = compareTerms(assignmentEntity.getAssignmentText(), textTerm);
            boolean executorContains = executorId == null ||
                    assignmentEntity.getExecutorAssignmentEntities()
                            .stream()
                            .filter(executorAssignmentEntity -> executorAssignmentEntity.
                                    getExecutor()
                                    .getId()
                                    .equals(executorId))
                            .findFirst().orElse(null) != null;
            boolean timeStart = fromDeadLine == null || fromDeadLine.isBefore(assignmentEntity.getAssignmentDeadLine());
            boolean timeEnd = toDeadline == null || toDeadline.isAfter(assignmentEntity.getAssignmentDeadLine());
            boolean typeContains = compareTerms(assignmentEntity.getType(), typeTerm);
            boolean authorContains = authorId == null || assignmentEntity.getAuthorId().equals(authorId);
            return textTermContains && executorContains && timeStart && timeEnd && typeContains && authorContains;
        }).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        return new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());
    }

    public AssignmentEntity updateAssignment(Long id, AssignmentFormDTO formDTO) {
        EmployeeEntity userBySession = authService.getUserBySession();
        AssignmentEntity assignment = assignmentEntityRepository.findById(id).orElse(null);

        if (assignment == null) {
            NotFoundException notFoundException = new NotFoundException("Assignment not found");
            log.error("Cant found Assignment", notFoundException);
            throw notFoundException;
        }

        if (!assignment.getAuthorId().equals(userBySession.getId()) && !userBySession.getRole().equals(ADMIN_ROLE)) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("Only author or admin can update this task");
            log.error("Try update task from non admin/author account",
                    forbiddenRequestException);
        }

        if (!assignment.getIsControl() && assignment.getIsDone() && !userBySession.getRole().equals(ADMIN_ROLE)) {
            BadRequestException badRequestException = new BadRequestException("Cant update while execution");
            log.error("Try update task while execution from non admin account", badRequestException);
            throw badRequestException;
        }

        if (formDTO.getDeadLine().isBefore(LocalDateTime.now())) {
            BadRequestException badRequestException = new BadRequestException("Deadline must be in future");
            log.error("Try create assignment with past deadline", badRequestException);
            throw badRequestException;
        }

        assignment.setAssignmentText(formDTO.getAbout());
        assignment.setAssignmentDeadLine(formDTO.getDeadLine());
        assignment.setType(formDTO.getType());
        assignment.setIsControl(false);
        assignment.setIsExecution(false);
        return assignmentEntityRepository.save(assignment);
    }

    public ResponseMessageDTO deleteAssignment(Long id) {
        EmployeeEntity userBySession = authService.getUserBySession();
        AssignmentEntity assignment = assignmentEntityRepository.findById(id).orElse(null);

        if (assignment == null) {
            NotFoundException notFoundException = new NotFoundException("Assignment not found");
            log.error("Cant found Assignment", notFoundException);
            throw notFoundException;
        }

        if (!assignment.getAuthorId().equals(userBySession.getId()) && !userBySession.getRole().equals(ADMIN_ROLE)) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("Only author or admin can delete this task");
            log.error("Try delete task from non admin/author account",
                    forbiddenRequestException);
        }

        if (assignment.getIsControl() || assignment.getIsExecution()) {
            BadRequestException badRequestException = new BadRequestException("Task in work");
            log.error("Try delete assignment while execution", badRequestException);
            throw badRequestException;
        }

        assignmentEntityRepository.delete(assignment);
        return ResponseMessageDTO.getMessage("Deleted");
    }

    public AssignmentEntity moveToWorkAssignment(Long id) {
        EmployeeEntity userBySession = authService.getUserBySession();
        AssignmentEntity assignment = assignmentEntityRepository.findById(id).orElse(null);

        if (assignment == null) {
            NotFoundException notFoundException = new NotFoundException("Assignment not found");
            log.error("Cant found Assignment", notFoundException);
            throw notFoundException;
        }

        if (assignment.getIsExecution() || assignment.getIsControl() || assignment.getIsDone()) {
            BadRequestException badRequestException =
                    new BadRequestException("Assignment already in execution or control or done");
            log.error("Try update state assignment while execution or control or done", badRequestException);
            throw badRequestException;
        }

        boolean isExecutor = assignment.getExecutorAssignmentEntities()
                .stream()
                .filter(executorAssignmentEntity -> executorAssignmentEntity.getExecutor()
                        .getId().equals(userBySession.getId()))
                .findFirst()
                .orElse(null) != null;

        if (isExecutor) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("Only executor can update state this task");
            log.error("Try to update state from non executor account",
                    forbiddenRequestException);
        }

        assignment.setIsExecution(true);
        return assignmentEntityRepository.save(assignment);

    }

    public AssignmentEntity setExecutorCheckWork(Long id, Boolean checkState) {
        EmployeeEntity userBySession = authService.getUserBySession();
        AssignmentEntity assignment = assignmentEntityRepository.findById(id).orElse(null);

        if (assignment == null) {
            NotFoundException notFoundException = new NotFoundException("Assignment not found");
            log.error("Cant found Assignment", notFoundException);
            throw notFoundException;
        }

        if (!assignment.getIsExecution()) {
            BadRequestException badRequestException =
                    new BadRequestException("Cant check assignment because is not in work");
            log.error("Try check assignment", badRequestException);
            throw badRequestException;
        }

        ExecutorAssignmentEntity executorAssignment =
                assignment.getExecutorAssignmentEntities()
                        .stream()
                        .filter(executorAssignmentEntity -> executorAssignmentEntity.getExecutor()
                                .getId().equals(userBySession.getId()))
                        .findFirst()
                        .orElse(null);

        if (executorAssignment == null) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("Only executor can update state this task");
            log.error("Try to update state from non executor account",
                    forbiddenRequestException);
            throw forbiddenRequestException;
        }

        executorAssignment.setIsDone(checkState);
        executorAssignmentEntityRepository.save(executorAssignment);

        boolean isCompleteDone =
                assignment.getExecutorAssignmentEntities().stream().allMatch(ExecutorAssignmentEntity::getIsDone);
        if (isCompleteDone) {
            assignment.setIsExecution(false);
            assignment.setIsControl(true);
            return assignmentEntityRepository.save(assignment);
        }
        return assignment;
    }

    private AssignmentEntity tryToSaveChanges(AssignmentEntity assignment) {
        EmployeeEntity userBySession = authService.getUserBySession();

        if (!assignment.getIsControl()) {
            BadRequestException badRequestException =
                    new BadRequestException("Cant update assignment from non control state");
            log.error("Try update state assignment", badRequestException);
            throw badRequestException;
        }

        if (!assignment.getAuthorId().equals(userBySession.getId())) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("Only author can update state this task");
            log.error("Try to update state from non author account",
                    forbiddenRequestException);
            throw forbiddenRequestException;
        }
        return assignmentEntityRepository.save(assignment);
    }

    public AssignmentEntity moveToReWorkAssignment(Long id) {
        AssignmentEntity assignment = assignmentEntityRepository.findById(id).orElse(null);
        if (assignment == null) {
            NotFoundException notFoundException = new NotFoundException("Assignment not found");
            log.error("Cant found Assignment", notFoundException);
            throw notFoundException;
        }
        assignment.setIsControl(false);
        assignment.setIsExecution(false);
        return tryToSaveChanges(assignment);
    }

    public AssignmentEntity moveToDoneAssignment(Long id) {
        AssignmentEntity assignment = assignmentEntityRepository.findById(id).orElse(null);
        if (assignment == null) {
            NotFoundException notFoundException = new NotFoundException("Assignment not found");
            log.error("Cant found Assignment", notFoundException);
            throw notFoundException;
        }
        assignment.setIsControl(false);
        assignment.setIsExecution(false);
        assignment.setIsDone(true);
        return tryToSaveChanges(assignment);
    }

    public AssignmentEntity assignUserToTask(Long id) {
        EmployeeEntity userBySession = authService.getUserBySession();
        AssignmentEntity assignment = assignmentEntityRepository.findById(id).orElse(null);

        if (assignment == null) {
            NotFoundException notFoundException = new NotFoundException("Assignment not found");
            log.error("Cant found Assignment", notFoundException);
            throw notFoundException;
        }

        boolean isOneOrg = assignment.getAuthor().getDepartment().getOrganizationId()
                .equals(userBySession.getDepartment().getOrganizationId());
        if (!isOneOrg) {
            BadRequestException badRequestException =
                    new BadRequestException("Cant assign to task from another organization");
            log.error("Try to assign task from another organization", badRequestException);
            throw badRequestException;
        }
        ExecutorAssignmentEntity executorAssignment = assignment
                .getExecutorAssignmentEntities()
                .stream()
                .filter(executorAssignmentEntity -> executorAssignmentEntity
                        .getExecutorUserId()
                        .equals(userBySession.getId()))
                .findFirst().orElse(null);
        if (executorAssignment != null) {
            BadRequestException badRequestException =
                    new BadRequestException("This account already assigned to this task");
            log.error("This account already assigned to this task", badRequestException);
            throw badRequestException;
        }
        ExecutorAssignmentEntity executorAssignmentEntity = new ExecutorAssignmentEntity();
        executorAssignmentEntity.setExecutorAssignmentId(assignment.getId());
        executorAssignmentEntity.setExecutorUserId(userBySession.getId());
        executorAssignmentEntityRepository.save(executorAssignmentEntity);

        return assignmentEntityRepository.getById(assignment.getId());
    }
}
