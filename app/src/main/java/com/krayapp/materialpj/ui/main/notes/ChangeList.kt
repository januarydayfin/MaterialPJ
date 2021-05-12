package com.krayapp.materialpj.ui.main.notes

data class ChangeList<out T>(
    val oldData: T,
    val newData: T
) {
    fun <T> createCombinedPayload(payloads: List<ChangeList<T>>): ChangeList<T> {
        assert(payloads.isNotEmpty())
        val firstChange = payloads.first()
        val lastChange = payloads.last()
        return ChangeList(firstChange.oldData, lastChange.newData)
    }
}
