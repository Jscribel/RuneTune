package com.jscribel.runetune

import kotlin.math.log2

private fun adjustOffset(pitch : Pitch, ratioFrequency : Double) : Pitch{
    pitch.setOffset(1200 * log2(ratioFrequency))
    return pitch
}

fun pitchCalculate (pitches : List<Pitch>, frequency : Double) : Pitch{
    lateinit var high : Pitch
    lateinit var low : Pitch
    var frequencyTemp : Double
    var foundHigh : Boolean = false
    var foundLow : Boolean = false
    for(pitch in pitches){
        frequencyTemp = pitch.getFrequencyRelative()
        if(frequency < frequencyTemp){
            high = pitch
            foundHigh = true
        }
        else if(frequency > frequencyTemp){
            low = pitch
            foundLow = true
        }
        else{
            return pitch.copy()
        }

        if(foundHigh  && foundLow){
            break
        }
    }
    return if(!foundHigh){
        adjustOffset((pitches[pitches.size - 1].copy()), frequency/low.getFrequencyRelative())
    }
    else if(!foundLow){
        adjustOffset(pitches[0].copy(), frequency/high.getFrequencyRelative())
    }
    else{
        val mid : Double = (high.getFrequencyRelative() + low.getFrequencyRelative())/2
        if(frequency >= mid){
            adjustOffset(high.copy(), frequency/high.getFrequencyRelative())
        }
        else{
            adjustOffset(low.copy(), frequency/low.getFrequencyRelative())
        }
    }
}