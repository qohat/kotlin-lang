package io.github.qohat

sealed interface Message {
    fun theMessage(): String
}
class Word(private val m: String): Message {
    override fun theMessage(): String =
        "The message: $m"
}
class Paragraph(private val m: String, private val n: Int): Message {
    override fun theMessage(): String =
        "The message: $m has $n letters"
}

class The(m: String): Message by Word(m)
class The1(m: String): Message by Paragraph(m, m.trim().length)

val a = The("The")
val b = The1("Let's write multiple words.")

fun main() = run {
    println(a.theMessage())
    println(b.theMessage())
}

