package com.tuulingo.whattoeat

//Max offset set by API is 900
class OffsetAmount() {

    private fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }

    fun offsetAmount(amount: Int): String {
        return rand(0, amount).toString()
    }
}