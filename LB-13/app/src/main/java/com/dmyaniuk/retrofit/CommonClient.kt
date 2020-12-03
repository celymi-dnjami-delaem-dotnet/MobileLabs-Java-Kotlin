package com.dmyaniuk.retrofit

class CommonClient(_link: String) {
    val genericRetrofitService: Services = RetrofitClient().getClient(_link).create(Services::class.java)
}