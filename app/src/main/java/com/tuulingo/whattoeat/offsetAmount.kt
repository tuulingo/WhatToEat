package com.tuulingo.whattoeat

class OffsetAmount() {

    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }

    fun offsetAmount(amount: Int): String {
        var rndNumber = rand(0, amount).toString()
        return rndNumber
    }
}