package com.onix.internship.arch.component.logger.configuration

import com.onix.internship.domain.component.logger.configuration.BaseLoggerConfiguration

class DefaultLoggerConfiguration(
    className: Any? = null,
    override val isEnabled: Boolean = true
) : BaseLoggerConfiguration() {

    override val className: String = getClassName(className ?: this)

    override fun copy(className: Any, isEnabled: Boolean) = DefaultLoggerConfiguration(this.className, isEnabled)

}