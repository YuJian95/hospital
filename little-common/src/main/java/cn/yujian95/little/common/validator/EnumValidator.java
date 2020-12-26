package cn.yujian95.little.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 校验指定数值范围的注解
 *
 * @author YuJian95  yujian95_cn@163.com
 * @date 2020/10/15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = EnumValidatorClass.class)
public @interface EnumValidator {
    String[] value() default {};

    String message() default "Enum value is not found!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
