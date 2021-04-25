package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.decagon.facilitymanagementapp_group_two.MainCoroutineRule
import com.decagon.facilitymanagementapp_group_two.getOrAwaitValueTest
import com.decagon.facilitymanagementapp_group_two.model.data.RequestBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.repository.FakeFacilityRepository
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SubmitRequestViewModelTest{
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var submitRequestViewModel: SubmitRequestViewModel

    @Before
    fun setup(){
        submitRequestViewModel = SubmitRequestViewModel(FakeFacilityRepository())
    }

    @Test
    fun `insert new request when no network should return network error`(){
        val value = submitRequestViewModel.postNewRequest("12345", RequestBody("Bad food","Get food",type="Food")).getOrAwaitValueTest()
        assertThat(value).isEqualTo(ResultStatus.NetworkError)
    }


}