package com.goodday.proj.api.order.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/verifyIamport")
public class VerifyController {

    // TODO 나중에 생성자 token 받기
    private IamportClient iamportClient;

    public VerifyController(){
        this.iamportClient = new IamportClient("...", "...");
    }

    /**
     * 프론트에서 받은 PG사 결괏값을 통해 아임포트 토큰 발행
     * @param imp_uid
     * @return
     * @throws IamportResponseException
     * @throws IOException
     */
    @PostMapping("/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        log.info("paymentByImpUid 진입");
        return iamportClient.paymentByImpUid(imp_uid);
    }
}
