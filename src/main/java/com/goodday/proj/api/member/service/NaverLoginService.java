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
public class NaverLoginService {

    public Map<String, String> naverAccessTokenAndGetNaverMemberInfo(String code, String state) throws IOException {
        String reqURL = "https://nid.naver.com/oauth2.0/token";

        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=JapyfHeRinhgQikh6zZV");
        sb.append("&client_secret=YVpruKPir6");
        sb.append("&state=" + state);
        sb.append("&redirect_uri=http://localhost:8080/api/member/kakao");
        sb.append("&code=" + code);
        bw.write(sb.toString());
        bw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode element = objectMapper.readTree(result);

        String access_Token = element.get("access_token").asText();
        String refresh_Token = element.get("refresh_token").asText();
        String token_type = element.get("token_type").asText();
        String expires_in = element.get("expires_in").asText();

        if (conn.getResponseCode() != 200) {
            String error = element.get("error").asText();
            String error_description = element.get("error_description").asText();
            log.error("ERROR_CODE: {} [{}]", error, error_description);
            throw new IllegalArgumentException(ErrorConst.loginErrorV2);
        }

        br.close();
        bw.close();

        Map<String, String> naverMemberInfo = naverMemberInfo(access_Token);
        return naverMemberInfo;
    }

    private Map<String, String> naverMemberInfo(String accessToken) throws IOException {
        String reqURL = "https://openapi.naver.com/v1/nid/me";

        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper parser = new ObjectMapper();
        JsonNode element = parser.readTree(result);

        Map<String, String> naverMember = new HashMap<>();
        naverMember.put("id", element.get("response").get("id").asText());
        naverMember.put("nickname", element.get("response").get("nickname").asText());
        naverMember.put("name", element.get("response").get("name").asText());
        naverMember.put("email", element.get("response").get("email").asText());
        String[] splitDashMobile = element.get("response").get("mobile").asText().split("-");
        String phone = splitDashMobile[0] + splitDashMobile[1] + splitDashMobile[2];
        naverMember.put("phone", phone);

        log.debug("{}", naverMember);

        br.close();
        return naverMember;
    }
}
