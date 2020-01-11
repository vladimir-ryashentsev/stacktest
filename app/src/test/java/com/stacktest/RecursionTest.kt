import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import org.junit.Test

class RecursionTest {
    @Test
    fun bla() {
        GlobalScope.launch {
            val state = async {
                val thread = object : Thread() {
                    var state = 0

                    override fun run() {
                        super.run()
                        for (i in 0..1000000)
                            state++
                    }
                }
                thread.start()
//                thread.join()
                while (thread.isAlive)
                    yield()
                thread.state
            }.await()
            println(state)
        }
        Thread.sleep(20000)
    }
}