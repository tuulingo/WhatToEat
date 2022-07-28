package com.tuulingo.whattoeat

import java.io.File

open class FileLogger {

     open fun logError(error: String){
        val file = File("Error.txt")
        file.appendText(
            text = error
        )
    }
}

class CustomFileLogger : FileLogger() {

    override fun logError(error: String){
        val file = File("Error2.txt")
        file.appendText(
            text = error
        )
    }
}