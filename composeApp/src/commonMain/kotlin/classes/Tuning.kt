package classes

data class Tuning(private var name : String, private var pitches : MutableList<Pitch> = mutableListOf<Pitch>()) {
    fun getName() : String{
        return name
    }
    fun getPitch(numberString : Int) : Pitch {
        return pitches[numberString - 1]
    }
    fun getPitches() : MutableList<Pitch>{
        return pitches
    }

    fun setName(name : String){
        this.name = name
    }

    override fun toString() : String{
        var output = "#\tPitch\n"
        for(i in 1..pitches.size){
            output += ("$i\t${pitches[i]}")
        }
        return output
    }
}