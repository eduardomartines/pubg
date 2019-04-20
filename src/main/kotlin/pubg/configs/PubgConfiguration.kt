package pubg.configs

import io.micronaut.context.annotation.ConfigurationProperties
import javax.validation.constraints.NotBlank

@ConfigurationProperties(PubgConfiguration.PREFIX)
class PubgConfiguration {

    companion object {
        const val PREFIX = "pubg_api"
    }

    @NotBlank
    var url: String? = null

    @NotBlank
    var key: String? = null

    @NotBlank
    var playersUrl: String? = null

    @NotBlank
    var matchUrl: String? = null

}