package com.ll.weflea.boundedContext.member.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.entity.ProfileImage;
import com.ll.weflea.boundedContext.member.repository.MemberRepository;
import com.ll.weflea.boundedContext.member.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileImageService {
    private final MemberRepository memberRepository;
    private final ProfileImageRepository profileImageRepository;

    @Value("${spring.servlet.multipart.location}")
    private String storageLocation;

    public Optional<ProfileImage> findByMember(Member member){
        return profileImageRepository.findByMember(member);
    }

    @Transactional
    public RsData<ProfileImage> uploadProfileImage(String username, MultipartFile image) throws IOException {

        Member member = memberRepository.findByUsername(username).orElse(null);

        // 기존 프로필 이미지가 있다면 삭제
        ProfileImage existingProfileImage = member.getProfileImage();
        if (existingProfileImage != null) {
            deleteProfileImage(existingProfileImage);
        }

        // 파일이 업로드되지 않았을 경우 디폴트 프로필 사진으로 업데이트
        if(image.isEmpty()){
            member.setDefaultImage(true);

            return RsData.of("S-2", "프로필 사진이 기본 이미지로 변경되었습니다.");
        }

        String fileName = generateUniqueFileName(image.getOriginalFilename());
        String filePath = storageLocation + fileName;

        File profileImg=  new File(filePath);
        image.transferTo(profileImg);

        // DB에 프로필 이미지 정보를 저장
        ProfileImage profileImage = ProfileImage
                .builder()
                .name(fileName)
                .path(filePath)
                .build();

        member.updateProfileImage(profileImage);

        profileImageRepository.save(profileImage);

       return RsData.of("S-1", "프로필 사진이 변경되었습니다.");
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String uniqueFileName = UUID.randomUUID().toString() + "." + extension;
        return uniqueFileName;
    }


    @Transactional
    public void deleteProfileImage(ProfileImage profileImage) {
        profileImage.updateMember(null);
        profileImageRepository.delete(profileImage);
        profileImageRepository.flush();
    }

    public ResponseEntity<byte[]> getProfileImg(String username) throws IOException {
        Member member = memberRepository.findByUsername(username).orElse(null);
        ProfileImage profileImage = profileImageRepository.findByMember(member).orElse(null);

        InputStream inputStream = new FileInputStream(profileImage.getPath());
        byte[] imageByteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
}
