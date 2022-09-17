package com.mungta.user.model;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, String>{

   Optional <UserEntity> findByUserId(String userId);
	UserEntity findByUserMailAddress(String userMailAddress);
   UserEntity findByUserIdOrUserMailAddress(String userId,String userMailAddress);
   UserEntity findByUserIdAndUserPassword(String userId,String userPassword);
   List<UserEntity> findAll();
   Boolean existsByUserId(String userId);
	Boolean existsByUserMailAddress(String userMailAddress);

   // @Query("select m from UserEntity m where m.userId = :userId")
   // UserEntity findUserPhotoByUserId(@Param("userId") String userId);

   // @Query("select m.userName from UserEntity m")
   // List<String> findUsernameList();

   // @Query("select new me.kyeongho.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
   // List<UserDto> findMemberDto();

   // @Query(value = "SELECT * FROM UserEntity WHERE USERNAME = ?0",
   //       nativeQuery = true)
   // UserEntity findByUsername(String userName);
}
