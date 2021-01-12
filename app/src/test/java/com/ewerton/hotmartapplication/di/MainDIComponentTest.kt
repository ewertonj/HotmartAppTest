package com.ewerton.hotmartapplication.di

fun configureTestAppComponent(baseApi: String)
        = listOf(
    MockWebServerDIPTest,
    configureNetworkModuleForTest(baseApi),
    UseCaseDependency,
    RepoDependency
)
