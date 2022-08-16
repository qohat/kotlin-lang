package io.github.qohat

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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

// Delegating values
class GonnaDelegate<A>(val value: A): ReadOnlyProperty<Any?, A> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): A {
        return value
    }
}
val c by GonnaDelegate("Hola")

fun main() = run {
    println(a.theMessage())
    println(b.theMessage())
    println(c.plus(" Hello"))
}

