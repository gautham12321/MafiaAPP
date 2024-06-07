package com.gautham.mafia.Provider

import com.gautham.mafia.Network.KtorRMC
import com.gautham.mafia.Network.RealTimeMessagingClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Provider{

    @Singleton
    @Provides
    fun HTTPCLientProvider(): HttpClient {
        return HttpClient(CIO){
            install(Logging)
            install(WebSockets)
        }
    }
    @Singleton
    @Provides
    fun RealtimeMessagingClientProvider(client: HttpClient):RealTimeMessagingClient{
        return KtorRMC(client)

    }


}