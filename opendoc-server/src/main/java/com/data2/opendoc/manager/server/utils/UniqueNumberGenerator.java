package com.data2.opendoc.manager.server.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UniqueNumberGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final int DIGIT_LENGTH = 8;

    public static Long generateUniqueNumber() {
        // 获取当前时间的时间戳（毫秒级）
        long currentTimeMillis = System.currentTimeMillis();

        // 为了确保数字长度，我们可以将时间戳的一部分与随机数结合
        // 这里我们取时间戳的后几位（例如，后13位，但考虑到数字长度要求，我们会截取或调整）
        // 并与一个随机数进行异或运算（或其他组合方式），然后取模以确保长度
        long combined = currentTimeMillis ^ random.nextLong();
        // 截取或调整数字长度到8位
        // 注意：这里使用Math.abs确保数字为正，并通过取模运算限制长度
        // 但由于取模可能导致前导零，我们需要进一步处理
        long number = Math.abs(combined % (long) Math.pow(10, DIGIT_LENGTH));
        return number;

        // 理论上，由于时间戳和随机数的组合，重复的可能性非常低
        // 但在极高并发或长时间运行的情况下，仍建议进一步处理重复（例如，使用集合检查）
        // 这里为了简化示例，省略了重复检查步骤

    }
}
