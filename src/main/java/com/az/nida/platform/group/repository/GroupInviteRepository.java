package com.az.nida.platform.group.repository;

import com.az.nida.platform.group.entity.GroupInvite;
import com.az.nida.platform.group.entity.InviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupInviteRepository extends JpaRepository<GroupInvite, Long> {

    List<GroupInvite> findByStudentId(Long studentId);

    List<GroupInvite> findByGroupId(Long groupId);

    List<GroupInvite> findByStudentIdAndStatus(Long studentId, InviteStatus status);

    Optional<GroupInvite> findByGroupIdAndStudentId(Long groupId, Long studentId);

    boolean existsByGroupIdAndStudentIdAndStatus(Long groupId, Long studentId, InviteStatus status);
}