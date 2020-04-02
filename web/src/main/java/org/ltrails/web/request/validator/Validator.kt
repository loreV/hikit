package org.ltrails.web.request.validator

interface Validator<T> {
    fun validate(request: T): List<String>
    fun getParams(request: T): List<String> {
        return emptyList()
    }
}