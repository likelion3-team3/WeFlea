package com.ll.weflea.boundedContext.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NicknameDto {

    @NotBlank(message="사용자 닉네임은 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "사용자 이름은 영어, 숫자, 한글만 가능합니다.")
    @Size(min = 3, max = 15, message = "최소 3글자 이상 최대 15글자 이하까지 가능합니다.")
    private String nickname;
}
