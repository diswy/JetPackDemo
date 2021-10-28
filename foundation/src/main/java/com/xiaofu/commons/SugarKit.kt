package com.xiaofu.commons

/**
 * 提高代码可读性的小封装
 * @author xiaofu
 * @date 2021.9.29
 */

inline fun <T1, T2> notNull(t1: T1?, t2: T2?, notNull: () -> Unit, isNull: () -> Unit) {
    if (t1 != null && t2 != null) {
        notNull.invoke()
    } else {
        isNull.invoke()
    }
}

inline fun <T1, T2, T3> notNull(
    t1: T1?, t2: T2?, t3: T3?,
    notNull: () -> Unit,
    isNull: () -> Unit
) {
    if (t1 != null && t2 != null && t3 != null) {
        notNull.invoke()
    } else {
        isNull.invoke()
    }
}

inline fun <T1, T2, T3, T4> notNull(
    t1: T1?, t2: T2?, t3: T3?, t4: T4?,
    notNull: () -> Unit,
    isNull: () -> Unit
) {
    if (t1 != null && t2 != null && t3 != null && t4 != null) {
        notNull.invoke()
    } else {
        isNull.invoke()
    }
}

inline fun <T1, T2, T3, T4, T5> notNull(
    t1: T1?, t2: T2?, t3: T3?, t4: T4?, t5: T5?,
    notNull: () -> Unit,
    isNull: () -> Unit
) {
    if (t1 != null && t2 != null && t3 != null && t4 != null && t5 != null) {
        notNull.invoke()
    } else {
        isNull.invoke()
    }
}

inline fun <T1, T2, T3, T4, T5, T6> notNull(
    t1: T1?, t2: T2?, t3: T3?, t4: T4?, t5: T5?, t6: T6?,
    notNull: () -> Unit,
    isNull: () -> Unit
) {
    if (t1 != null && t2 != null && t3 != null && t4 != null && t5 != null && t6 != null) {
        notNull.invoke()
    } else {
        isNull.invoke()
    }
}


