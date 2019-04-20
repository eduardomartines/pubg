package pubg

import io.micronaut.context.annotation.ConfigurationProperties


@ConfigurationProperties(PubgConfiguration.PREFIX)
class PubgConfiguration {

    companion object {
        const val PREFIX = "pubg_api"
        const val URL = "https://api.pubg.com"
    }

//    @NotBlank
    var url: String? = null

//    @NotBlank
    var key: String? = null

    fun toMap(): MutableMap<String, Any> {
        val m = HashMap<String, Any>()

        if (url != null) {
            m["url"] = url!!
        }
        if (key!= null) {
            m["key"] = key!!
        }

        return m
    }

}