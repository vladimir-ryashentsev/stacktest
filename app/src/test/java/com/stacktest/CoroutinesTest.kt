package com.stacktest

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutinesTest {

    @Test
    fun bla3() {
        var counter = 0
        runBlocking {
            withContext(Dispatchers.Default) {
                massiveRun {
                    counter++
                }
            }
            println("Counter = $counter")
        }
    }

    // Message types for counterActor
    sealed class CounterMsg {
        object IncCounter : CounterMsg() // one-way message to increment counter
        class GetCounter(val response: CompletableDeferred<Int>) :
            CounterMsg() // a request with reply
    }

    @Test
    fun bla2() {

        CoroutineScope(Dispatchers.IO).launch {
            val actor = counterActor() // create the actor
            massiveRun {
                actor.send(CounterMsg.IncCounter)
            }
            // send a message to get a counter value from an actor
            val response = CompletableDeferred<Int>()
            actor.send(
                CounterMsg.GetCounter(
                    response
                )
            )
            println("Counter = ${response.await()}")
            actor.close() // shutdown the actor
        }

        Thread.sleep(3000)
    }

    fun CoroutineScope.counterActor() = actor<CounterMsg>(context = Dispatchers.Default) {
        var counter = 0 // actor state

        for (msg in channel) { // iterate over incoming messages
            when (msg) {
                CounterMsg.IncCounter -> counter++
                is CounterMsg.GetCounter -> msg.response.complete(counter)
            }
        }
    }

    suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100  // number of coroutines to launch
        val k = 1000 // times an action is repeated by each coroutine
        val time = measureTimeMillis {
            coroutineScope {
                // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) { action() }
                    }
                }
            }
        }
        println("Completed ${n * k} actions in $time ms")
    }

    @Test
    fun bla() {
        try {
            GlobalScope.launch {
                launch {
                    launch {
                        while (true) {
                            delay(500)
                            println(coroutineContext[Job]!!.isCancelled)
                        }
                    }
                    launch {
                        delay(2000)
                        throw Exception("boo")
                    }
                }

                delay(2000)
            }
        } catch (e: Exception) {
            println("CATCH!")
        }


        Thread.sleep(20000)
    }
}