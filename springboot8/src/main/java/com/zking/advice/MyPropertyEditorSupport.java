package com.zking.advice;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;

public class MyPropertyEditorSupport extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            setValue(null); // 空字符串，设置为空
        } else {
            setValue(Arrays.asList(text.split(" "))); // 转换为list集合
        }
    }
}
