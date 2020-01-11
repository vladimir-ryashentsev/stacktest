package com.stacktest

import org.junit.Test
import kotlin.concurrent.thread

class ThreadsTest {

    var counter = 0
    val obj = Any()

    @Test
    fun bla() {
        val t1 = thread {
            for (i in 1..50000) {
                synchronized(obj) {
                    counter++
                }
            }
        }
        val t2 = thread {
            for (i in 1..50000) {
                synchronized(obj) {
                    counter++
                }
            }
        }
        val t3 = thread {
            for (i in 1..50000) {
                synchronized(obj) {
                    counter++
                }
            }
        }
        val t4 = thread {
            for (i in 1..50000) {
                synchronized(obj) {
                    counter++
                }
            }
        }
        t1.join()
        t2.join()
        t3.join()
        t4.join()
        println("threads launched")
        println("counter = $counter")
    }
}