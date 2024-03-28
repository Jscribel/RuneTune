package com.jscribel.runetune

data class Tuning(private var name : String, private var pitches : MutableList<Pitch>) {
    fun getName() : String{
        return name
    }
    fun getPitch(numberString : Int) : Pitch{
        return pitches[numberString]
    }
    fun getPitches() : List<Pitch>{
        return pitches
    }

    fun setName(name : String){
        this.name = name
    }
    fun setPitch(numberString : Int, pitch : Pitch){
        pitches[numberString] = pitch
    }
    fun setPitches(pitches : MutableList<Pitch>){
        this.pitches = pitches
    }

    override fun toString() : String{
        var output = "#\tPitch\n"
        for(i in 1..pitches.size){
            output += ("$i\t${pitches[i]}")
        }
        return output
    }
}