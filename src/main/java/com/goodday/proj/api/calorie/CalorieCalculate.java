package com.goodday.proj.api.calorie;

import com.goodday.proj.api.calorie.dto.CaloriePrescribeForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CalorieCalculate {
    public Map calculate(CaloriePrescribeForm form) {
        double bmr = 0;
        if (form.getGender().equals("M")) {
            bmr = maleBmr(form);
        } else {
            bmr = femaleBmr(form);
        }

        double maintenance = maintenanceCalorie(bmr, form.getActivity());

        double targetCalorie = targetCalorie(maintenance, form.getTarget());

        double carbsCalorie = targetCalorie * 0.5;
        double proteinCalorie = targetCalorie * 0.3;
        double fatCalorie = targetCalorie * 0.2;

        double carbsGram = dietGram(carbsCalorie, 4.0);
        double proteinGram = dietGram(proteinCalorie, 4.0);
        double fatGram = dietGram(fatCalorie, 9.0);

        Map map = new HashMap<>();
        map.put("gender", form.getGender());
        map.put("weight", form.getWeight());
        map.put("height", form.getHeight());
        map.put("age", form.getAge());
        map.put("activity", form.getActivity());
        map.put("target", form.getTarget());
        map.put("bmr", bmr);
        map.put("maintenance", maintenance);
        map.put("targetCalorie", targetCalorie);
        map.put("carbsCalorie", carbsCalorie);
        map.put("proteinCalorie", proteinCalorie);
        map.put("fatCalorie", fatCalorie);
        map.put("carbsGram", carbsGram);
        map.put("proteinGram", proteinGram);
        map.put("fatGram", fatGram);

        return map;
    }

    private double dietGram(double nutrient, double kcal) {
        return nutrient / kcal;
    }

    private double targetCalorie(double maintenance, String target) {
        double targetCalorie = 0;
        if (target.equals("증량")) {
            targetCalorie = maintenance + maintenance * 0.15;
        } else if (target.equals("감량")) {
            targetCalorie = maintenance - maintenance * 0.15;
        } else {
            targetCalorie = maintenance;
        }
        return targetCalorie;
    }

    private double maintenanceCalorie(double bmr, Integer activity) {
        double maintenance = 0;
        if (activity == 1) {
            maintenance = bmr * 1.2;
        } else if (activity == 2) {
            maintenance = bmr * 1.375;
        } else if (activity == 3) {
            maintenance = bmr * 1.55;
        } else if (activity == 4) {
            maintenance = bmr * 1.725;
        } else if (activity == 5) {
            maintenance = bmr * 1.9;
        }
        return maintenance;
    }

    private double femaleBmr(CaloriePrescribeForm form) {
        return 655 + (9.6 * form.getWeight()) + (1.7 * form.getHeight()) - (4.7 * form.getAge());
    }

    private double maleBmr(CaloriePrescribeForm form) {
        return 66 + (13.7 * form.getWeight()) + (5 * form.getHeight()) - (6.8 * form.getAge());
    }
}
