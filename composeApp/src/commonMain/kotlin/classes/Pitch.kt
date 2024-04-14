package classes

import kotlin.math.pow

data class Pitch(private val note : String, private val octave : Long, private val frequencyRelative : Double, private var offset : Double = 0.0){
    fun getNote() : String{
        return note
    }
    fun getFrequency() : Double{
        return ((2.0).pow(offset/1200) * frequencyRelative)
    }
    fun getFrequencyRelative() : Double{
        return frequencyRelative
    }
    fun getOctave() : Long{
        return octave
    }
    fun getOffset() : Double{
        return offset
    }

    fun setOffset(cents : Double){
        offset = cents
    }

    override fun toString() : String{
        return "$note $octave"
    }
}
