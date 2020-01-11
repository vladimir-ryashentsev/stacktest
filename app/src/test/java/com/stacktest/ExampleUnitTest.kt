package com.stacktest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun bla() = runBlocking<Unit> {

        val workChannel = Channel<Int>()
        val consumeChannel = Channel<Int>()

        repeat(1) {
            worker(workChannel, consumeChannel)
        }

        val consumer = consumer(consumeChannel)

        launch {
            var count = 0;
            for (i in 0..4) {
                delay(500)
                count++
                println("create work: $count")
                workChannel.send(count)
            }
        }
        delay(600)
        workChannel.cancel()
        consumeChannel.cancel()
        delay(5000)
    }

    private fun CoroutineScope.worker(workChannel: ReceiveChannel<Int>, resultChannel: SendChannel<Int>) =
        launch {
                for (num in workChannel) {
                    println("do work: $num")
                    resultChannel.send(num * num)
                }
                println("worker finished")
        }

    private fun CoroutineScope.consumer(inChannel: ReceiveChannel<Int>) = launch {
        for (num in inChannel)
            println("consume work $num")
        println("consumer finished")
    }

}
