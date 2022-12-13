package com.example.myapplication.myapplication.usamas.domain.use_cases.business

import com.example.myapplication.myapplication.usamas.data.models.RequestNewsIo
import com.example.myapplication.myapplication.usamas.data.models.RequestNewsOrg
import com.example.myapplication.myapplication.usamas.domain.entities.*
import com.example.myapplication.myapplication.usamas.domain.repositories.BusinessDataRepo
import com.example.myapplication.myapplication.usamas.domain.repositories.BusinessRepo
import com.example.myapplication.myapplication.usamas.network.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetDataUseCase {
    fun execute(requestNewsOrg : RequestNewsIo): Flow<Resource<ResponseBodyIo<List<NewsDataModel>>>>
}


class GetDataUseCaseImpl @Inject constructor(private val businessRepo: BusinessDataRepo) :
    GetDataUseCase {
    override fun execute(requestNewsOrg : RequestNewsIo) = businessRepo.getDataApi(requestNewsOrg)
}