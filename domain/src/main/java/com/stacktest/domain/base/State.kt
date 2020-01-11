package com.stacktest.domain.base

sealed class State {
    object Success : State()
    object InProgress : State()
    class Error(val message: String) : State()
}