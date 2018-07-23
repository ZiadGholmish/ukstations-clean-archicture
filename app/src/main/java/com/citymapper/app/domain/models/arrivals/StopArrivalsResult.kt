package com.citymapper.app.domain.models.arrivals

import com.citymapper.app.domain.models.stoppoint.StopPoint
import com.citymapper.app.domain.models.stoppoint.StopPointsResult

sealed class StopArrivalsResult

sealed class StopArrivalsPayLoad : StopArrivalsResult() {
    data class Data(val data: List<ArrivalTimeModel>) : StopArrivalsPayLoad()
}

/**
 * format the error that come from the api so i can handle
 * and remove un necessary condition in on error function
 */
 sealed class StopArrivalNetworkHttpError : StopArrivalsResult() {
    data class Error(val code: Int, val message: String) : StopArrivalNetworkHttpError()
}


