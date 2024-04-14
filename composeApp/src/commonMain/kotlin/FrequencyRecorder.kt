import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class FrequencyRecorder{
    fun getFrequency() : Double{
        return (1..8000).random().toDouble()
    }
}