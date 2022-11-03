package com.onix.internship.arch.router

import com.onix.internship.arch.router.command.Command

interface Router {

    fun navigate(command: Command.Route)

    fun popBackStack(): Boolean

    fun popToRoot()

    companion object {
        const val UNKNOWN_ROUTE = "Unknown for this route"
        const val UNKNOWN_COMMAND = "Unknown command"
    }
}