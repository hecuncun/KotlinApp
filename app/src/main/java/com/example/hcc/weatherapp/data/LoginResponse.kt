package com.example.hcc.weatherapp.data

/**
 * Created by hecuncun on 2019/9/16
 */
data class LoginResponse(
        val code: Int,
        val message: String,
        val data: DataBean
)


data class DataBean(
        val token: String,
        val socketport: Int,
        val user: User
)

data class User(
        val id: Int,
        val loginName: String,
        val realName: String,
        val org: Int,
        val orgName: String,
        val roles: String,
        val orgEntity: OrgEntity
)

data class OrgEntity(
        val id: Int,
        val orgName: String,
        val orgCode: String,
        val orgOrder: Int,
        val orgDesc: String,
        val lng: String,
        val lat: String,
        val radius: String,
        val parent: Int,
        val parentName: String
)