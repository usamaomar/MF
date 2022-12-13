package com.example.myapplication.myapplication.usamas.domain.repositories

import com.example.myapplication.myapplication.usamas.data.models.RequestNewsIo
import com.example.myapplication.myapplication.usamas.data.models.RequestNewsOrg
import com.example.myapplication.myapplication.usamas.domain.entities.NewsModel
import com.example.myapplication.myapplication.usamas.domain.entities.ResponseBody
import com.example.myapplication.myapplication.usamas.domain.entities.ResponseBodyOrg
import com.example.myapplication.myapplication.usamas.network.Resource
import kotlinx.coroutines.flow.Flow

interface BusinessRepo {

    fun getNewsApi(requestNewsOrg : RequestNewsOrg): Flow<Resource<ResponseBodyOrg<List<NewsModel>>>>


}