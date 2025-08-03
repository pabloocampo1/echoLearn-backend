package com.EchoLearn_backend.application.helpers;

import org.springframework.stereotype.Component;

@Component
public class ExamHelper {

    public Boolean validateIfWinTheTestByLevelAndResult(String level, Double examResult) {
        switch (level.toLowerCase()) {
            case "easy":
                return examResult >= 70;
            case "intermediate":
                return examResult >= 80;
            case "hard":
                return examResult >= 90;
            default:
                return false;
        }
    }

}
