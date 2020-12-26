package cn.yujian95.little.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author YuJian95  yujian95_cn@163.com
 * @date 2020/10/15
 */

public class EnumValidatorClass implements ConstraintValidator<EnumValidator, Integer> {

    private String[] values;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        boolean isValid = false;

        // 当状态为空时使用默认值
        if (value == null) {
            return true;
        }

        for (String s : values) {
            if (s.equals(String.valueOf(value))) {
                isValid = true;
                break;
            }
        }

        return isValid;
    }

    @Override
    public void initialize(EnumValidator enumValidator) {
        this.values = enumValidator.value();
    }
}
