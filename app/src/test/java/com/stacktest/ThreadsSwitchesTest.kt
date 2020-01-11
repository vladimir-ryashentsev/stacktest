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
class ThreadsSwitchesTest {

    @Test
    fun channels() = runBlocking<Unit> {
        val workChannel = Channel<Int>(10000)
        val receiveChannel = workChannel as ReceiveChannel<Int>
        val sendChannel = workChannel as SendChannel<Int>

        worker(receiveChannel)
        workProducer(sendChannel)

        delay(1000)
        workChannel.cancel()
    }

    private fun CoroutineScope.worker(channel: ReceiveChannel<Int>) = launch {
        for (num in channel)
            println(num)
    }

    private fun CoroutineScope.workProducer(channel: SendChannel<Int>) = launch {
        for (num in 0..100000)
            channel.send(num)
    }
}
