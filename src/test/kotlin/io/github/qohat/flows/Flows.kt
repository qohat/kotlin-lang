package io.github.qohat.flows

import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

data class Record(val message: String)

class Flows : StringSpec({

    val record = Record("My Message")
    fun invalidFlow() = flow<Record> { record }.map { it.message } // Doesn´t emit anything
    fun validFlow() = flow { emit(record) }.map { it.message }
    fun bigFlow() = (1..1000).asFlow().map { Record("Record $it") }

    ""

})