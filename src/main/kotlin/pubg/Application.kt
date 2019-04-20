package pubg

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("pubg")
                .mainClass(Application.javaClass)
                .start()
    }
}