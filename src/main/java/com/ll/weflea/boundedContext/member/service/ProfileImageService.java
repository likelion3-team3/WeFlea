package com.ll.weflea.boundedContext.member.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.entity.ProfileImage;
import com.ll.weflea.boundedContext.member.repository.MemberRepository;
import com.ll.weflea.boundedContext.member.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional
    public RsData<ProfileImage> uploadProfileImage(String username, MultipartFile image){


        Member member = memberRepository.findByUsername(username).orElse(null);

        String fileName = generateUniqueFileName(image.getOriginalFilename());
        String filePath = storageLocation + fileName;

        log.debug("========================================filePath : " + filePath);


        // DB에 프로필 이미지 정보를 저장
        ProfileImage profileImage = ProfileImage
                .builder()
                .member(member)
                .name(fileName)
                .path(filePath)
                .build();
        profileImageRepository.save(profileImage);
       return RsData.of("S-1", "프로필 사진이 변경되었습니다.");
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String uniqueFileName = UUID.randomUUID().toString() + "." + extension;
        return uniqueFileName;
    }

}
