package com.dev.batu.scheduler.dto.scheduler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * @created 25/05/2025 ~~ 11:49
 * author: batu
 */
@Getter
public class UpdateCronWithTaskNameDto {

    private final String beanName;
    private final String methodName;
    private final String cronExpression;

    private final String taskName;

    public UpdateCronWithTaskNameDto(String beanName, String methodName, String cronExpression) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.cronExpression = cronExpression;
        this.taskName = beanName + "#" + methodName;
    }
}
