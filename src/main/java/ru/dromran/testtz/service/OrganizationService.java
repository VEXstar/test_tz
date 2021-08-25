package ru.dromran.testtz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.dromran.testtz.dto.OrganizationFormDTO;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.entity.OrganizationEntity;
import ru.dromran.testtz.exception.BadRequestException;
import ru.dromran.testtz.exception.ForbiddenRequestException;
import ru.dromran.testtz.exception.NotFoundException;
import ru.dromran.testtz.repository.EmployeeEntityRepository;
import ru.dromran.testtz.repository.OrganizationEntityRepository;

import static ru.dromran.testtz.constants.RoleConstants.ADMIN_ROLE;

@Service
@Slf4j
public class OrganizationService {

    @Autowired
    AuthService authService;

    @Autowired
    EmployeeEntityRepository employeeEntityRepository;

    @Autowired
    OrganizationEntityRepository organizationEntityRepository;

    public OrganizationEntity createOrganization(OrganizationFormDTO organizationFormDTO) {
        EmployeeEntity userBySession = authService.getUserBySession();

        if (!userBySession.getRole().equals(ADMIN_ROLE)) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("You dont have enough permissions to perform this operation");
            log.error("Try create organization from non admin account", forbiddenRequestException);
            throw forbiddenRequestException;
        }

        EmployeeEntity chef = employeeEntityRepository.findById(organizationFormDTO.getChefUser()).orElse(null);
        if (chef == null) {
            NotFoundException userNotFound = new NotFoundException("User not found");
            log.error("Cant find user", userNotFound);
            throw userNotFound;
        }

        OrganizationEntity byName =
                organizationEntityRepository.getOrganizationEntityByName(organizationFormDTO.getName());
        if (byName != null) {
            BadRequestException badRequestException =
                    new BadRequestException("The organization with the specified name already exists");
            log.error("Try to create organization with exists name", badRequestException);
            throw badRequestException;
        }

        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setChefId(chef.getId());
        organizationEntity.setName(organizationFormDTO.getName());
        organizationEntity.setPhysicalAddress(organizationFormDTO.getPhysicalAddress());
        organizationEntity.setLegalAddress(organizationFormDTO.getLegalAddress());

        return organizationEntityRepository.save(organizationEntity);
    }

    public Page<OrganizationEntity> findOrganizations(Integer page,
                                                      Integer pageSize,
                                                      String nameTerm,
                                                      String address,
                                                      Long chefId) {
        int realPage = page == null ? 0 : page;
        int realSize = pageSize == null ? 10 : pageSize;
        Pageable pageable = PageRequest.of(realPage, realSize);

        EmployeeEntity chefById = employeeEntityRepository.getById(chefId);

        return organizationEntityRepository.
                findOrganizationEntitiesByNameContainingAndPhysicalAddressContainingAndLegalAddressContainingAndChef(
                        nameTerm, address, address, chefById, pageable);
    }

    public OrganizationEntity getOrganizationById(Long id) {
        OrganizationEntity byId = organizationEntityRepository.findById(id).orElse(null);

        if (byId == null) {
            NotFoundException notFoundException = new NotFoundException("Organization not found");
            log.error("Cant found organization", notFoundException);
            throw notFoundException;
        }
        return byId;
    }

    public ResponseMessageDTO deleteOrganization(Long id) {
        EmployeeEntity userBySession = authService.getUserBySession();

        OrganizationEntity byId = organizationEntityRepository.findById(id).orElse(null);

        if (byId == null) {
            NotFoundException notFoundException = new NotFoundException("Organization not found");
            log.error("Cant found organization", notFoundException);
            throw notFoundException;
        }

        if (!userBySession.getRole().equals(ADMIN_ROLE) || !byId.getChef().getId().equals(userBySession.getId())) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("You dont have enough permissions to perform this operation");
            log.error("Try create organization from non admin account", forbiddenRequestException);
            throw forbiddenRequestException;
        }
        organizationEntityRepository.delete(byId);
        return ResponseMessageDTO.getMessage("organization deleted");
    }

    public OrganizationEntity updateOrganization(Long id, OrganizationFormDTO organizationFormDTO) {
        EmployeeEntity userBySession = authService.getUserBySession();

        OrganizationEntity byId = organizationEntityRepository.findById(id).orElse(null);

        if (byId == null) {
            NotFoundException notFoundException = new NotFoundException("Organization not found");
            log.error("Cant found organization", notFoundException);
            throw notFoundException;
        }

        if (!userBySession.getRole().equals(ADMIN_ROLE) || !byId.getChef().getId().equals(userBySession.getId())) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("You dont have enough permissions to perform this operation");
            log.error("Try update organization from non admin account", forbiddenRequestException);
            throw forbiddenRequestException;
        }

        OrganizationEntity byName =
                organizationEntityRepository.getOrganizationEntityByName(organizationFormDTO.getName());
        if (byName != null) {
            BadRequestException badRequestException =
                    new BadRequestException("The organization with the specified name already exists");
            log.error("Try to update organization with exists name", badRequestException);
            throw badRequestException;
        }

        byId.setName(organizationFormDTO.getName());
        byId.setPhysicalAddress(organizationFormDTO.getPhysicalAddress());
        byId.setLegalAddress(organizationFormDTO.getLegalAddress());

        if (!byId.getChef().getId().equals(organizationFormDTO.getChefUser())) {
            EmployeeEntity newChef = employeeEntityRepository
                    .findById(organizationFormDTO.getChefUser()).orElse(null);
            if (newChef == null) {
                NotFoundException notFoundException = new NotFoundException("New chef user not found");
                log.error("Cant found user", notFoundException);
                throw notFoundException;
            }
            byId.setChefId(newChef.getId());
        }
        return organizationEntityRepository.save(byId);
    }
}
