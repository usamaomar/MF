package com.example.myapplication.myapplication.usamas.data.repositories

import com.example.myapplication.myapplication.usamas.data.models.RequestNewsIo
import com.example.myapplication.myapplication.usamas.data.models.RequestNewsOrg
import com.example.myapplication.myapplication.usamas.domain.entities.NewsModel
import com.example.myapplication.myapplication.usamas.domain.entities.ResponseBodyOrg
import com.example.myapplication.myapplication.usamas.domain.repositories.BusinessRepo
import com.example.myapplication.myapplication.usamas.network.Resource
import com.example.myapplication.myapplication.usamas.network.UrlFactory
import com.example.myapplication.myapplication.usamas.network.networkApiCall
import com.example.myapplication.myapplication.usamas.network.services.BusinessAPI
import com.example.myapplication.myapplication.usamas.network.services.BusinessService
import com.example.myapplication.myapplication.usamas.network.toUrl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BusinessRepoImpl @Inject constructor(
    private val businessService: BusinessService,
    private val urlFactory: UrlFactory,
) : BusinessRepo {
    override fun getNewsApi(requestNewsOrg: RequestNewsOrg) = networkApiCall(createCall = {
        businessService.getNewsApi(
            BusinessAPI.Everything.toUrl(urlFactory),
            requestNewsOrg.query.toString(),
            requestNewsOrg.from.toString(),
            requestNewsOrg.sortBy.toString(),
            requestNewsOrg.apiKey.toString()
        )
    }, saveCallResult = {
        print("")
    })

}