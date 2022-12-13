package com.example.myapplication.myapplication.usamas.domain.use_cases.business

import com.example.myapplication.myapplication.usamas.data.models.RequestNewsOrg
import com.example.myapplication.myapplication.usamas.domain.entities.NewsModel
import com.example.myapplication.myapplication.usamas.domain.entities.ResponseBody
import com.example.myapplication.myapplication.usamas.domain.entities.ResponseBodyOrg
import com.example.myapplication.myapplication.usamas.domain.repositories.BusinessRepo
import com.example.myapplication.myapplication.usamas.network.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface GetNewsUseCase {
    fun execute(requestNewsOrg : RequestNewsOrg): Flow<Resource<ResponseBodyOrg<List<NewsModel>>>>
}


class GetNewsUseCaseImpl @Inject constructor(private val businessRepo: BusinessRepo) :
    GetNewsUseCase {
    override fun execute(requestNewsOrg : RequestNewsOrg) = businessRepo.getNewsApi(requestNewsOrg)
}