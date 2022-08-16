package io.github.qohat.flows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.delayEach
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Record(val message: String)

object ExploreFlow {
    private val record = Record("My Message")
    fun invalidFlow() = flow<Record> { record }.map { it.message } // Doesn´t emit anything
    fun validFlow() = flow { emit(record) }.map { it.message }
    fun bigFlow() = (1..1000).asFlow().map { Record("Record $it") }
}

fun main1() = println(ExploreFlow.validFlow()) // prints My Message

fun main2() = runBlocking(Dispatchers.Unconfined) {
    // Launch a concurrent coroutine to check if the main thread is blocked
    launch {
        for (k in 1..3) {
            printlnInThread("I'm not blocked $k")
            delay(100)
        }
    }

    //Wait because shares thread with the launch block above
    launch {
        for (k in 1..3) {
            printlnInThread("2 I'm not blocked $k")
            delay(100)
        }
    }

    //Run in parallel with everything because has it´s own context
    launch(Dispatchers.Default) {
        for (k in 1..3) {
            printlnInThread("Running on my context $k")
            delay(100)
        }
    }
    // Collect the flow
    ExploreFlow
        .bigFlow()
        .flowOn(Dispatchers.IO) //Reserve a context for the flow
        .collect(::printlnInThread)
}

fun main() = runBlocking {

    println("Flow running")
    ExploreFlow
        .bigFlow()
        .take(5)
        .onEach { delay(1000) } // onEach Inherit the father block thread
        .flowOn(Dispatchers.Unconfined)
        .collect(::printlnInThread)

    /*ExploreFlow
        .bigFlow()
        .take(5)
        .onEach { delay(1000) }
        .flowOn(Dispatchers.Default)
        .collect(::printlnInThread)*/

}


private fun printlnInThread(line: Any) {
    println("Thread: ${Thread.currentThread().name} - line: $line")
}
