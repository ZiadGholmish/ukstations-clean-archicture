package com.citymapper.app.presentation.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.citymapper.app.domain.models.stoppoint.StopPointModel
import com.citymapper.app.domain.models.stoppoint.StopPointsResult
import com.citymapper.app.domain.usecase.FetchArrivalTimesUseCase
import com.citymapper.app.domain.usecase.FetchStopPointsUseCase
import com.citymapper.app.presentation.views.nearbystations.NearbyStationsVM
import com.citymapper.app.utils.RxImmediateSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class NearbyStationsVMUnitTesting {

    inline fun <reified T> lambdaMock(): T = mock(T::class.java)


    @get:Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var observer: Observer<List<StopPointModel>>


    @Mock
    private lateinit var fetchArrivalTimesUseCase: FetchArrivalTimesUseCase

    @Mock
    private lateinit var stopPointResult: StopPointsResult

    @Mock
    private lateinit var stopPointsUseCase: FetchStopPointsUseCase

    private val nearbyStationsVM by lazy { NearbyStationsVM(stopPointsUseCase, fetchArrivalTimesUseCase) }

    @Before
    fun initTest() {
        reset(stopPointsUseCase, observer)
    }

//    @Test
//    fun testCallingMethod() {
//
//        val fakeObservable = Observable.just(stopPointResult)
//        `when`(stopPointsUseCase.fetchStopPoints(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyDouble(), ArgumentMatchers.anyDouble()))
//                .thenReturn(fakeObservable)
//        nearbyStationsVM.stopPointsLiveData.observeForever(observer)
//        nearbyStationsVM.loadStopPointsByLocation(0.0, 0.0)
//
//
//    }

}