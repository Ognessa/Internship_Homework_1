package com.onix.internship.network

import com.onix.internship.data.network.configuration.NetworkConfiguration

class PersonDataConfiguration:  NetworkConfiguration.SimpleConfiguration(){
    override val server = "https://api.agify.io"
}