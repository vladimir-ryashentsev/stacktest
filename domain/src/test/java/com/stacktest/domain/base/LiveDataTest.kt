package com.stacktest.domain.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.rules.Timeout

open class LiveDataTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var globalTimeout: Timeout = Timeout.seconds(2) // 10 seconds max per method tested

}