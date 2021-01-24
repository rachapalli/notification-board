package com.board.notification.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.GroupRepo;
import com.board.notification.dao.UserRepo;
import com.board.notification.exception.AlreadyExistsException;
import com.board.notification.exception.DataNotFoundException;
import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.ActiveStatusEnum;
import com.board.notification.model.Groups;
import com.board.notification.model.StatusEnum;
import com.board.notification.model.Users;
import com.board.notification.model.dto.EmailDTO;
import com.board.notification.model.dto.EmailStatusDTO;
import com.board.notification.model.dto.GroupDTO;
import com.board.notification.model.dto.GroupUsersDTO;
import com.board.notification.model.dto.NotificationConverter;
import com.board.notification.service.EmailService;
import com.board.notification.service.GroupService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.NotificationUtils;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepo groupRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private Environment env;

	@Transactional
	@Override
	public GroupDTO createGroup(GroupDTO groupDTO) {
		if (groupDTO.getGroupId() != null) {
			throw new InvalidRequestException("Group Cannot be updated");
		}
		Groups createdGroup = null;
		validateGroup(groupDTO);
		groupDTO.setCreatedDate(NotificationUtils.getUKTime());
		groupDTO.setIsActive(ActiveStatusEnum.ACTIVE.statusFlag());
		Groups groups = new Groups();
		BeanUtils.copyProperties(groupDTO, groups);
		createdGroup = groupRepo.save(groups);
		if (createdGroup != null) {
			groupRepo.addGroupUser(groupDTO.getCreatedBy(), createdGroup.getGroupId(), groupDTO.getCreatedBy(),
					NotificationUtils.getUKTime(), ActiveStatusEnum.ACTIVE.status());
		}
		groupDTO.setGroupId(createdGroup.getGroupId());
		return groupDTO;
	}

	@Override
	@Transactional
	public GroupDTO updateGroup(GroupDTO groupDTO) {
		if (groupDTO.getGroupId() == null) {
			throw new InvalidRequestException("To update group GroupId" + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		Optional<Groups> groupOptional = groupRepo.findById(groupDTO.getGroupId());
		if (groupOptional.isPresent()) {
			Groups groups = groupOptional.get();
			if (!groupDTO.getGroupName().equals(groups.getGroupName())) {
				validateGroup(groupDTO);
			}
			groups.setGroupName(groupDTO.getGroupName());
			groups.setIsPublic(groupDTO.getIsPublic());
			Groups savedGroup = groupRepo.save(groups);
			groupDTO.setCreatedDate(savedGroup.getCreatedDate());
			groupDTO.setCreatedBy(savedGroup.getCreatedBy());
		} else {
			throw new DataNotFoundException("Group id" + NotificationConstants.MSG_NOT_FOUND);
		}
		return groupDTO;
	}

	@Transactional
	@Override
	public GroupDTO deleteGroup(GroupDTO groupDTO) {
		if (groupDTO.getGroupId() == null) {
			throw new InvalidRequestException("To update group GroupId" + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		Optional<Groups> groupOptional = groupRepo.findById(groupDTO.getGroupId());
		if (groupOptional.isPresent()) {
			Groups groups = groupOptional.get();
			groups.setIsActive(ActiveStatusEnum.INACTIVE.statusFlag());
			groupRepo.save(groups);
		} else {
			throw new DataNotFoundException("Group id" + NotificationConstants.MSG_NOT_FOUND);
		}
		return groupDTO;
	}
	
	@Override
	public StatusEnum approveGroup(GroupDTO groupDTO) {
		StatusEnum statusEnum = StatusEnum.FAIL;
		if (groupDTO == null) {
			throw new InvalidRequestException("Invalid Request data");
		} else {
			if (groupDTO.getGroupName() == null || groupDTO.getGroupName().isEmpty()) {
				throw new InvalidRequestException("groupName" + NotificationConstants.MSG_NOT_NULL_EMPTY);
			}

			if (groupDTO.getIsApproved() == null) {
				throw new InvalidRequestException("IsApproved is required");
			}
		}
		Groups group = groupRepo.findByGroupName(groupDTO.getGroupName());
		if (group == null) {
			throw new DataNotFoundException("groupName" + NotificationConstants.MSG_NOT_FOUND);
		}
		group.setIsApproved(groupDTO.getIsApproved());
		groupRepo.save(group);
		Optional<Users> boardOwnerOpt = userRepo.findById(group.getCreatedBy());
		if (boardOwnerOpt.isPresent()) {
			Users boardOwner = boardOwnerOpt.get();
			EmailDTO emailDTO = new EmailDTO(boardOwner.getEmail(),
					env.getProperty(NotificationConstants.DB_PROP_PO_GRP_APPR_EMAIL_SUBJECT),
					preparePOApprovalEmailBody(boardOwner.getUserName(), groupDTO.getIsApproved(), group.getGroupName()));
			EmailStatusDTO sentEmailStatus = emailService.sendHtmlEmail(emailDTO);
			statusEnum = sentEmailStatus == null ? statusEnum : sentEmailStatus.getStatus();
		} else {
			throw new DataNotFoundException("Board Owner" + NotificationConstants.MSG_NOT_FOUND);
		}
		return statusEnum;
	}

	private boolean validateGroup(GroupDTO group) {
		if (groupRepo.findByGroupName(group.getGroupName()) != null) {
			throw new AlreadyExistsException(NotificationConstants.MSG_GROUP_EXISTS);
		}
		return false;
	}

	@Override
	public List<GroupDTO> getAllGroups() {
		return NotificationConverter.toGroupDTOs(groupRepo.findAll());
	}

	@Override
	public List<GroupDTO> getUserGroups(String emailId) {
		return NotificationConverter.toGroupDTOs(groupRepo.getAllUserGroupsByEmail(emailId));
	}

	@Override
	public List<GroupDTO> getOwnerGroups(String emailId) {
		return NotificationConverter.toGroupDTOs(groupRepo.getBoardOwnerGroups(emailId));
	}

	@Override
	public GroupDTO findByGroupName(String groupName) {
		if (groupName == null || groupName.isEmpty()) {
			throw new InvalidRequestException("groupName " + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		Groups group = groupRepo.findByGroupName(groupName);
		if (group == null) {
			throw new DataNotFoundException("Group: " + groupName + NotificationConstants.MSG_NOT_FOUND);
		}
		return NotificationConverter.toGroupDto(group);
	}
	
	@Override
	public List<GroupUsersDTO> getGroupUsers(String email) {
		Users user = userRepo.findByEmail(email);
		if (user == null) {
			throw new DataNotFoundException(NotificationConstants.INVALID_USER_EMAIL);
		}
		return groupRepo.getGroupUsers(user.getUserId());
	}
	
	@Override
	public void addGroupUser(Integer userId, Integer groupId, Integer createdBy, Boolean isActive) {
		groupRepo.addGroupUser(userId, groupId, createdBy, NotificationUtils.getUKTime(),
				isActive ? ActiveStatusEnum.ACTIVE.status() : ActiveStatusEnum.INACTIVE.status());
	}
	
	@Override
	public GroupDTO getGroupByName(String groupName) {
		if (groupName == null || groupName.isEmpty()) {
			throw new InvalidRequestException(NotificationConstants.REQUIRED_MSG + "groupName");
		}
		Groups group = groupRepo.findByGroupName(groupName);
		if (group == null) {
			throw new DataNotFoundException("Group not found with name:" + groupName);
		}
		return NotificationConverter.toGroupDto(group);
	}

	@Override
	public boolean checkUserGroupAccess(String userEmail, Integer groupId) {
		return groupRepo.checkUserGroupAccess(userEmail, groupId) > 0 ? true : false; 
	}

	
	private String preparePOApprovalEmailBody(String userName, Boolean isApproved, String groupName) {
		String message = new String(env.getProperty(NotificationConstants.DB_PROP_PO_GRP_APPR_EMAIL_BODY));
		message = message.replace(NotificationConstants.PH_USER_NAME, userName).replace(NotificationConstants.PH_BNAME, groupName).replace(
				NotificationConstants.PH_APPR_DESC, isApproved ? NotificationConstants.DESC_APPROVED : NotificationConstants.DESC_DISAPPROVED);
		return message;
	}
}
