package com.taekscode.network.response

class Either<out T>(val value: Any?) {

    val isSuccess: Boolean get() = value !is Failure

    val isFailure: Boolean get() = value is Failure

    @Suppress("UNCHECKED_CAST")
    fun getOrNull(): T? = when {
        isFailure -> null
        else -> value as T
    }

    fun exceptionOrNull(): Throwable? = when (value) {
        is Failure -> value.exception
        else -> null
    }

    override fun toString(): String =
        when (value) {
            is Failure -> value.toString() // "Failure($exception)"
            else -> "Success($value)"
        }

    companion object {
        fun <T> success(value: T): Either<T> = Either(value)

        fun <T> failure(exception: Throwable): Either<T> = Either(createFailure(exception))
    }

    class Failure(val exception: Throwable) {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }
}

private fun createFailure(exception: Throwable): Any = Either.Failure(exception)

inline fun <reified R, reified T> Either<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
): R {
    return when (val exception = exceptionOrNull()) {
        null -> onSuccess(value as T)
        else -> onFailure(exception)
    }
}

inline fun <reified R, reified T> Either<T>.map(transform: (value: T) -> R): Either<R> {
    return when {
        isSuccess -> Either.success(transform(value as T))
        else -> Either(value)
    }
}

inline fun <reified T> Either<T>.onFailure(action: (exception: Throwable) -> Unit): Either<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

inline fun <reified T> Either<T>.onSuccess(action: (value: T) -> Unit): Either<T> {
    if (isSuccess) action(value as T)
    return this
}

inline fun <reified T> Either<T>.getOrThrow(): T {
    throwOnFailure()
    return value as T
}

fun Either<*>.throwOnFailure() {
    if (value is Either.Failure) throw value.exception
}

inline fun <reified R, reified T : R> Either<T>.getOrElse(onFailure: (exception: Throwable) -> R): R {
    return when (val exception = exceptionOrNull()) {
        null -> value as T
        else -> onFailure(exception)
    }
}

inline fun <reified R, reified T : R> Either<T>.getOrDefault(defaultValue: R): R {
    if (isFailure) return defaultValue
    return value as T
}