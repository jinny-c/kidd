package com.kidd.base.common.utils;

import java.util.Arrays;

public class ToStringUtils {
    private final String className;
    private ToStringUtils.ValueHolder holderHead;
    private ToStringUtils.ValueHolder holderTail;
    private boolean omitNullOrBlankValues;

    public ToStringUtils(String className) {
        this.holderHead = new ToStringUtils.ValueHolder();
        this.holderTail = this.holderHead;
        this.omitNullOrBlankValues = false;
        this.className = className;
    }
    
    public ToStringUtils(Object obj) {
        this.holderHead = new ToStringUtils.ValueHolder();
        this.holderTail = this.holderHead;
        this.omitNullOrBlankValues = false;
        //this.className = obj.getClass().getName();
        this.className = obj.getClass().getSimpleName();
    }

    public ToStringUtils omitNullOrBlankValues() {
        this.omitNullOrBlankValues = true;
        return this;
    }

    public ToStringUtils add(String name, Object value) {
        return this.addHolder(name, value);
    }

    public ToStringUtils add(String name, boolean value) {
        return this.addHolder(name, String.valueOf(value));
    }

    public ToStringUtils add(String name, char value) {
        return this.addHolder(name, String.valueOf(value));
    }

    public ToStringUtils add(String name, double value) {
        return this.addHolder(name, String.valueOf(value));
    }

    public ToStringUtils add(String name, float value) {
        return this.addHolder(name, String.valueOf(value));
    }

    public ToStringUtils add(String name, int value) {
        return this.addHolder(name, String.valueOf(value));
    }

    public ToStringUtils add(String name, long value) {
        return this.addHolder(name, String.valueOf(value));
    }

    public ToStringUtils addValue(Object value) {
        return this.addHolder(value);
    }

    public ToStringUtils addValue(boolean value) {
        return this.addHolder(String.valueOf(value));
    }

    public ToStringUtils addValue(char value) {
        return this.addHolder(String.valueOf(value));
    }

    public ToStringUtils addValue(double value) {
        return this.addHolder(String.valueOf(value));
    }

    public ToStringUtils addValue(float value) {
        return this.addHolder(String.valueOf(value));
    }

    public ToStringUtils addValue(int value) {
        return this.addHolder(String.valueOf(value));
    }

    public ToStringUtils addValue(long value) {
        return this.addHolder(String.valueOf(value));
    }

    public String toString() {
        boolean omitNullOrBlankValuesSnapshot = this.omitNullOrBlankValues;
        String nextSeparator = "";
        StringBuilder builder = (new StringBuilder(32)).append(this.className).append('{');

        for(ToStringUtils.ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
            Object value = valueHolder.value;
            if (!omitNullOrBlankValuesSnapshot || value != null && value != "") {
                builder.append(nextSeparator);
                nextSeparator = ", ";
                if (valueHolder.name != null) {
                    builder.append(valueHolder.name).append('=');
                }

                if (value != null && value.getClass().isArray()) {
                    Object[] objectArray = new Object[]{value};
                    String arrayString = Arrays.deepToString(objectArray);
                    builder.append(arrayString.substring(1, arrayString.length() - 1));
                } else {
                    builder.append(value);
                }
            }
        }

        return builder.append('}').toString();
    }

    private ToStringUtils.ValueHolder addHolder() {
        ToStringUtils.ValueHolder valueHolder = new ToStringUtils.ValueHolder();
        this.holderTail = this.holderTail.next = valueHolder;
        return valueHolder;
    }

    private ToStringUtils addHolder(Object value) {
        ToStringUtils.ValueHolder valueHolder = this.addHolder();
        valueHolder.value = value;
        return this;
    }

    private ToStringUtils addHolder(String name, Object value) {
        ToStringUtils.ValueHolder valueHolder = this.addHolder();
        valueHolder.value = value;
        valueHolder.name = name;
        return this;
    }

    private static final class ValueHolder {
        String name;
        Object value;
        ToStringUtils.ValueHolder next;

        private ValueHolder() {
        }
    }
}
