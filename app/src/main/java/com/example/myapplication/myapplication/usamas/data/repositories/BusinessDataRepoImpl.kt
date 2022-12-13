package com.example.myapplication.myapplication.usamas.data.repositories

import com.example.myapplication.myapplication.usamas.data.models.RequestNewsIo
import com.example.myapplication.myapplication.usamas.data.models.RequestNewsOrg
import com.example.myapplication.myapplication.usamas.domain.entities.NewsModel
import com.example.myapplication.myapplication.usamas.domain.entities.ResponseBodyOrg
import com.example.myapplication.myapplication.usamas.domain.repositories.BusinessDataRepo
import com.example.myapplication.myapplication.usamas.domain.repositories.BusinessRepo
import com.example.myapplication.myapplication.usamas.network.Resource
import com.example.myapplication.myapplication.usamas.network.UrlFactory
import com.example.myapplication.myapplication.usamas.network.networkApiCall
import com.example.myapplication.myapplication.usamas.network.services.BusinessAPI
import com.example.myapplication.myapplication.usamas.network.services.BusinessDataAPI
import com.example.myapplication.myapplication.usamas.network.services.BusinessDataService
import com.example.myapplication.myapplication.usamas.network.toUrl
import javax.inject.Inject


class BusinessDataRepoImpl @Inject constructor(
    private val businessService: BusinessDataService,
    private val urlFactory: UrlFactory,
) : BusinessDataRepo {

    override fun getDataApi(requestNewsOrg: RequestNewsIo) = networkApiCall(createCall = {
        businessService.getDataApi(
            BusinessDataAPI.News.toUrl(urlFactory),
            requestNewsOrg.query.toString(),
            requestNewsOrg.apiKey.toString()
        )
    }, saveCallResult = {
        print("")
    })
}