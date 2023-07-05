package com.goodday.proj.api.calorie.controller;

import com.goodday.proj.api.calorie.CalorieCalculate;
import com.goodday.proj.api.calorie.dto.CaloriePrescribeForm;
import com.goodday.proj.constant.ErrorConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/calorie")
@RequiredArgsConstructor
public class CalorieController {

    private final CalorieCalculate calorieCalculate;

    /**
     * 칼로리 계산
     * @param form
     * @param bindingResult
     * @return Map
     */
    @PostMapping("/prescribe")
    public Map calculateCalorie(@Valid @RequestBody CaloriePrescribeForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.nullError);
        }

        return calorieCalculate.calculate(form);
    }


}
