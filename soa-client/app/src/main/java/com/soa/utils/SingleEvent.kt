package com.soa.utils

class SingleEvent<out T>(private val content: T) {

    private var isHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    val consumeContent: T?
        get() = if (!isHandled) {
            isHandled = true
            content
        } else {
            null
        }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}