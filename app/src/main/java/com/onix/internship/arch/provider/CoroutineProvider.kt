package com.onix.internship.arch.provider

import com.onix.internship.arch.coroutine.CoroutineLauncher

interface CoroutineProvider {
    val launcher: CoroutineLauncher
}