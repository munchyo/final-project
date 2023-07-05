package com.goodday.proj.api.member.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodday.proj.constant.ErrorConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    public String getKakaoAccessToken(String code) throws IOException {
        String access_Token="";
        String refresh_Token ="";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=5f4936ea948f79e01ae85e35eff3a8e3");
        sb.append("&redirect_uri=http://localhost:8080/api/member/kakao");
        sb.append("&code=" + code);
        bw.write(sb.toString());
        bw.flush();

        //결과 코드가 200이라면 성공
        if (conn.getResponseCode() != 200) {
            throw new IllegalArgumentException(ErrorConst.loginErrorV2);
        }

        //request JSON response 메세지 읽어오기
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        //JSON 파싱 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode element = objectMapper.readTree(result);

        access_Token = element.get("access_token").asText();
        refresh_Token = element.get("refresh_token").asText();

        System.out.println("access_token : " + access_Token);
        System.out.println("refresh_token : " + refresh_Token);

        br.close();
        bw.close();
        return access_Token;
    }

    public Map<String, Object> getKakaoMemberInfo(String token) throws IOException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + token);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper parser = new ObjectMapper();
        JsonNode element = parser.readTree(result);

        Map<String, Object> kakaoMember = new HashMap<>();
        kakaoMember.put("id", element.get("id").asLong());
        kakaoMember.put("nickname", element.get("properties").get("nickname").asText());
        String email = "";
        if (element.get("kakao_account").get("has_email").asBoolean()) {
            email = element.get("kakao_account").get("email").asText();
        }
        kakaoMember.put("email", email);

        br.close();

        return kakaoMember;
    }

}
