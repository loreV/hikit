package org.hikit.common.service

import org.hikit.common.configuration.ConfigurationProperties
import org.hikit.common.configuration.ConfigurationProperties.LOCAL_IP_ADDRESS
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.common.service.response.AltitudeApiResponse
import java.net.URL
import javax.inject.Inject
import javax.inject.Named

class AltitudeServiceHelper @Inject constructor(@Named(ConfigurationProperties.ALTITUDE_SERVICE_PROPERTY) altitudeServicePort: String,
                                                private val gsonBeanHelper: GsonBeanHelper) {

    private val pathToServiceApi: String = "$LOCAL_IP_ADDRESS:$altitudeServicePort/api/v1/lookup"

    fun getAltitudeByLongLat(latitude: Double,
                             longitude: Double): Double {
        val apiGetEndpoint = "http://$pathToServiceApi?locations=$latitude,$longitude"
        val getCall = URL(apiGetEndpoint).readText()
        val gsonBuilder: AltitudeApiResponse = gsonBeanHelper.gsonBuilder!!.fromJson(getCall, AltitudeApiResponse::class.java)
        return gsonBuilder.results.first().elevation
    }
}