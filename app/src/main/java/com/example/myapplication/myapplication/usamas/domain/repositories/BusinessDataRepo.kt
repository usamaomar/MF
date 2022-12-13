package com.example.myapplication.myapplication.usamas.domain.repositories

import com.example.myapplication.myapplication.usamas.data.models.RequestNewsIo
import com.example.myapplication.myapplication.usamas.data.models.RequestNewsOrg
import com.example.myapplication.myapplication.usamas.domain.entities.*
import com.example.myapplication.myapplication.usamas.network.Resource
import kotlinx.coroutines.flow.Flow

interface BusinessDataRepo {

    fun getDataApi(requestNewsOrg : RequestNewsIo): Flow<Resource<ResponseBodyIo<List<NewsDataModel>>>>

}